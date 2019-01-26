package com.alexparpas.media

import com.alexparpas.media.twitch.ui.media.TwitchMediaViewModelFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

internal object Injection {

    fun provideMediaViewModelFactory(gameId: String) =
            TwitchMediaViewModelFactory(
                    gameId = gameId,
                    ioScheduler = Schedulers.io(),
                    uiScheduler = AndroidSchedulers.mainThread(),
                    twitchMediaRepository = MediaTwitch.twitchMediaRepository
            )

}