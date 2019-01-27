package com.alexparpas.media.twitch.ui.media

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.alexparpas.media.twitch.data.GameStreamMinimal
import com.alexparpas.media.twitch.data.TwitchMediaRepository
import com.alexparpas.media.twitch.data.toMinimal
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
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
    private val _streamsLiveData = MutableLiveData<List<GameStreamMinimal>>()
    val streamsLiveData: LiveData<List<GameStreamMinimal>> = _streamsLiveData

    init {
        getStreams()
    }

    private fun getStreams() {
        twitchMediaRepository.getStreamsById(clientId, gameId)
                .map { it.map { streams -> streams.toMinimal() } }
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