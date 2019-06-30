package com.alexparpas.media.twitch.ui.media.more

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexparpas.media.twitch.core.data.TwitchMapper
import com.alexparpas.media.twitch.core.data.TwitchMediaRepository
import com.alexparpas.media.twitch.core.data.model.MediaType
import com.alexparpas.media.twitch.core.data.model.VideoItem
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

class TwitchMediaMoreViewModel(
        private val gameId: String,
        private val categoryName: String,
        private val mediaType: MediaType,
        private val ioScheduler: Scheduler,
        private val uiScheduler: Scheduler,
        private val repository: TwitchMediaRepository,
        private val mapper: TwitchMapper
) : ViewModel() {
    private val disposables = CompositeDisposable()

    private val _videosLiveData = MutableLiveData<List<VideoItem>>()
    val videosLiveData: LiveData<List<VideoItem>> = _videosLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    val categoryNameLiveData = MutableLiveData<String>()

    init {
        getMedia()
        categoryNameLiveData.value = categoryName
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    private fun getMedia() {
        getMediaStream()
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({
                    _videosLiveData.value = it
                }, {
                    _errorLiveData.value = getErrorMessage(it)
                }).let {
                    disposables.add(it)
                }
    }

    private fun getMediaStream(): Single<List<VideoItem>> =
            when (mediaType) {
                MediaType.LIVE -> repository.getLiveStreamsByName(gameId).map { mapper.mapStreams(it) }
                MediaType.VOD -> repository.getVideosByGame(gameId).map { mapper.mapVideos(it) }
                MediaType.CLIP -> repository.getClips(gameId).map { mapper.mapClips(it) }
            }

    private fun getErrorMessage(throwable: Throwable): String {
        return "Something went wrong."
    }
}

@Suppress("UNCHECKED_CAST")
class TwitchMoreViewModelFactory(
        private val gameId: String,
        private val categoryName: String,
        private val mediaType: MediaType,
        private val ioScheduler: Scheduler,
        private val uiScheduler: Scheduler,
        private val repository: TwitchMediaRepository,
        private val mapper: TwitchMapper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TwitchMediaMoreViewModel(
                gameId,
                categoryName,
                mediaType,
                ioScheduler,
                uiScheduler,
                repository,
                mapper
        ) as T
    }
}