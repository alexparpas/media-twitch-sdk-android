package com.alexparpas.media.twitch.ui.media

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.alexparpas.media.twitch.data.TwitchMediaRepository
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

class TwitchMediaViewModel(
        private val gameId: String,
        private val ioScheduler: Scheduler,
        private val uiScheduler: Scheduler,
        private val twitchMediaRepository: TwitchMediaRepository
) : ViewModel() {
    private val disposables = CompositeDisposable()

    init {

    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}

@Suppress("UNCHECKED_CAST")
class TwitchMediaViewModelFactory(
        private val gameId: String,
        private val ioScheduler: Scheduler,
        private val uiScheduler: Scheduler,
        private val twitchMediaRepository: TwitchMediaRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TwitchMediaViewModel(
                gameId,
                ioScheduler,
                uiScheduler,
                twitchMediaRepository
        ) as T
    }
}