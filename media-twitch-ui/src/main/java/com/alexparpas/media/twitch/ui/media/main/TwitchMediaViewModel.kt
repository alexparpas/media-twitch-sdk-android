package com.alexparpas.media.twitch.ui.media.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexparpas.media.twitch.core.*
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function3
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class TwitchMediaViewModel(
        private val clientId: String,
        private val gameId: String,
        private val ioScheduler: Scheduler,
        private val uiScheduler: Scheduler,
        private val twitchMediaRepository: TwitchMediaRepository
) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val _streamsLiveData = MutableLiveData<List<MediaItem>>()
    val streamsLiveData: LiveData<List<MediaItem>> = _streamsLiveData

    init {
        getLiveStreams()
    }

    private fun getLiveStreams() {
        Single.zip(
                twitchMediaRepository.getLiveStreamsById(clientId, gameId),
                twitchMediaRepository.getClips(clientId, gameId),
                twitchMediaRepository.getVideosByGame(clientId, gameId),
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
                CategoryItem("Streams"),
                MediaBindingItem(streams.map { it.toVideoBinding() }),
                CategoryItem("Clips"),
                MediaBindingItem(clips.map { it.toVideoBinding() }),
                CategoryItem("Videos"),
                MediaBindingItem(videos.map { it.toVideoBinding() })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}

@Suppress("UNCHECKED_CAST")
class TwitchMediaViewModelFactory(
        private val clientId: String,
        private val gameId: String,
        private val ioScheduler: Scheduler,
        private val uiScheduler: Scheduler,
        private val twitchMediaRepository: TwitchMediaRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TwitchMediaViewModel(
                clientId,
                gameId,
                ioScheduler,
                uiScheduler,
                twitchMediaRepository
        ) as T
    }
}