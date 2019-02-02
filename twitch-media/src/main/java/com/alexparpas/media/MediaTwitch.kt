package com.alexparpas.media

import android.app.Application
import android.content.Context
import android.content.Intent
import com.alexparpas.media.twitch.R
import com.alexparpas.media.twitch.data.TwitchMediaRepository
import com.alexparpas.media.twitch.data.TwitchMediaService
import com.alexparpas.media.twitch.ui.media.TwitchMediaFragment
import com.alexparpas.media.twitch.ui.media.TwitchMediaViewModelFactory
import com.alexparpas.media.twitch.ui.video.TwitchPlayerActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object MediaTwitch {
    private lateinit var app: Application
    private lateinit var clientId: String
    internal const val ARG_CHANNEL_ID = "ARG_CHANNEL_ID"
    internal const val ARG_GAME_ID = "ARG_GAME_ID"

    val twitchMediaRepository by lazy {
        TwitchMediaRepository(Injection.getTwitchService())
    }

    fun init(app: Application, clientId: String) {
        this.app = app
        this.clientId = clientId
    }

    fun getTwitchMediaFragment(gameId: String) = TwitchMediaFragment.newInstance(gameId)

    fun playVideo(context: Context, channelName: String) {
        context.startActivity(
                Intent(context, TwitchPlayerActivity::class.java).apply {
                    putExtra(ARG_CHANNEL_ID, channelName)
                }
        )
    }

    internal object Injection {

        fun provideMediaViewModelFactory(gameId: String) =
                TwitchMediaViewModelFactory(
                        clientId = clientId,
                        gameId = gameId,
                        ioScheduler = Schedulers.io(),
                        uiScheduler = AndroidSchedulers.mainThread(),
                        twitchMediaRepository = twitchMediaRepository
                )

        fun getTwitchService() =
                Retrofit.Builder()
                        .baseUrl(app.getString(R.string.twitch_api_base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()
                        .create(TwitchMediaService::class.java)!!
    }
}