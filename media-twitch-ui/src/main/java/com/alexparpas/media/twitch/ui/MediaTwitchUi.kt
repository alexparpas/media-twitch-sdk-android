package com.alexparpas.media.twitch.ui

import android.content.Context
import android.content.Intent
import com.alexparpas.media.twitch.core.MediaTwitch
import com.alexparpas.media.twitch.ui.media.main.TwitchMediaFragment
import com.alexparpas.media.twitch.ui.media.main.TwitchMediaViewModelFactory
import com.alexparpas.media.twitch.ui.video.TwitchPlayerActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object MediaTwitchUi {
    internal const val ARG_LINK = "ARG_LINK"
    internal const val ARG_GAME_ID = "ARG_GAME_ID"

    fun getTwitchMediaFragment(gameId: String) = TwitchMediaFragment.newInstance(gameId)

    fun playVideo(context: Context, url: String) {
        context.startActivity(
                Intent(context, TwitchPlayerActivity::class.java).apply {
                    putExtra(ARG_LINK, url)
                }
        )
    }

    internal object Injection {
        fun provideMediaViewModelFactory(gameId: String) =
                TwitchMediaViewModelFactory(
                        clientId = MediaTwitch.clientId,
                        gameId = gameId,
                        ioScheduler = Schedulers.io(),
                        uiScheduler = AndroidSchedulers.mainThread(),
                        twitchMediaRepository = MediaTwitch.twitchMediaRepository
                )
    }
}