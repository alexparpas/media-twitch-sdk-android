package com.alexparpas.media.twitch.ui.media.more

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexparpas.media.twitch.core.MediaType
import com.alexparpas.media.twitch.core.TwitchMediaRepository
import com.alexparpas.media.twitch.core.VideoBinding
import com.alexparpas.media.twitch.core.toVideoBinding
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

class TwitchMediaMoreViewModel(
        private val gameId: String,
        private val categoryName: String,
        private val mediaType: MediaType,
        private val ioScheduler: Scheduler,
        private val uiScheduler: Scheduler,
        private val repository: TwitchMediaRepository
) : ViewModel() {
    private val disposables = CompositeDisposable()

    private val _videosLiveData = MutableLiveData<List<VideoBinding>>()
    val videosLiveData: LiveData<List<VideoBinding>> = _videosLiveData

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

    private fun getMediaStream(): Single<List<VideoBinding>> =
            when (mediaType) {
                MediaType.LIVE -> repository.getLiveStreamsByName(gameId)
                        .map { streams ->
                            streams.map { it.toVideoBinding() }
                        }
                MediaType.VOD -> repository.getVideosByGame(gameId)
                        .map { vods ->
                            vods.map { it.toVideoBinding() }
                        }
                MediaType.CLIP -> repository.getClips(gameId)
                        .map { clips ->
                            clips.map { it.toVideoBinding() }
                        }
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
        private val repository: TwitchMediaRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TwitchMediaMoreViewModel(
                gameId,
                categoryName,
                mediaType,
                ioScheduler,
                uiScheduler,
                repository
        ) as T
    }
}