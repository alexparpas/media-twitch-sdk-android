package com.alexparpas.media

import android.app.Application
import android.content.Context
import android.content.Intent
import com.alexparpas.media.twitch.data.TwitchMediaRepository
import com.alexparpas.media.twitch.ui.media.TwitchMediaFragment
import com.alexparpas.media.twitch.ui.video.TwitchPlayerActivity

object MediaTwitch {
    private lateinit var app: Application
    internal const val ARG_CHANNEL_ID = "ARG_CHANNEL_ID"
    internal const val ARG_GAME_ID = "ARG_GAME_ID"

    val twitchMediaRepository by lazy { TwitchMediaRepository() }

    fun init(app: Application) {
        this.app = app
    }

    fun getMediaFragment(gameId: String) = TwitchMediaFragment()

    fun launchMediaActivity(context: Context, gameId: String) {
        context.startActivity(
                Intent(context, TwitchPlayerActivity::class.java).apply {
                    putExtra(ARG_GAME_ID, gameId)
                }
        )
    }

    fun playVideo(context: Context, channelId: String) {
        context.startActivity(
                Intent(context, TwitchPlayerActivity::class.java).apply {
                    putExtra(ARG_CHANNEL_ID, channelId)
                }
        )
    }

}