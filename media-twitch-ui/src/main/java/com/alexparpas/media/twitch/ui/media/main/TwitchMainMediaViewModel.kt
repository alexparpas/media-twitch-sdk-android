package com.alexparpas.media.twitch.ui.media.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexparpas.media.twitch.core.data.TwitchMapper
import com.alexparpas.media.twitch.core.data.TwitchMediaRepository
import com.alexparpas.media.twitch.core.data.model.*
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function3
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class TwitchMainMediaViewModel(
        private val gameId: String,
        private val ioScheduler: Scheduler,
        private val uiScheduler: Scheduler,
        private val twitchMediaRepository: TwitchMediaRepository,
        private val mapper: TwitchMapper
) : ViewModel() {
    private val disposables = CompositeDisposable()

    private val _streamsLiveData = MutableLiveData<List<MediaItem>>()
    val streamsLiveData: LiveData<List<MediaItem>> = _streamsLiveData

    init {
        getLiveStreams()
    }

    private fun getLiveStreams() {
        Single.zip(
                twitchMediaRepository.getLiveStreamsById(gameId),
                twitchMediaRepository.getClips(gameId),
                twitchMediaRepository.getVideosByGame(gameId),
                Function3<List<Stream>, List<Clip>, List<Video>, List<MediaItem>> { streams, clips, videos ->
                    transformData(streams, clips, videos)
                })
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribeBy(
                        onSuccess = {
                            _streamsLiveData.value = it
                        },
                        onError = {
                            Timber.e(it)
                        }
                ).addTo(disposables)
    }

    private fun transformData(streams: List<Stream>, clips: List<Clip>, videos: List<Video>): List<MediaItem> {
        return listOf(
                CategoryItem("Streams", MediaType.LIVE),
                MediaBindingItem(mapper.mapStreams(streams)),
                CategoryItem("Videos", MediaType.VOD),
                MediaBindingItem(mapper.mapVideos(videos)),
                CategoryItem("Clips", MediaType.CLIP),
                MediaBindingItem(mapper.mapClips(clips))
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}

@Suppress("UNCHECKED_CAST")
class TwitchMainMediaViewModelFactory(
        private val gameId: String,
        private val ioScheduler: Scheduler,
        private val uiScheduler: Scheduler,
        private val twitchMediaRepository: TwitchMediaRepository,
        private val mapper: TwitchMapper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TwitchMainMediaViewModel(
                gameId,
                ioScheduler,
                uiScheduler,
                twitchMediaRepository,
                mapper
        ) as T
    }
}