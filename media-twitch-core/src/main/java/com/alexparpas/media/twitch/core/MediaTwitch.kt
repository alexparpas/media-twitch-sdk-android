package com.alexparpas.media.twitch.core

import android.app.Application
import com.alexparpas.media.twitch.core.data.TwitchMediaLocalStorage
import com.alexparpas.media.twitch.core.data.TwitchMediaRepository
import com.alexparpas.media.twitch.core.data.TwitchMediaService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object MediaTwitch {
    private lateinit var app: Application
    lateinit var clientId: String

    val twitchMediaRepository by lazy {
        TwitchMediaRepository(
                clientId,
                Injection.getTwitchService(),
                TwitchMediaLocalStorage()
        )
    }

    fun init(app: Application, clientId: String) {
        this.app = app
        this.clientId = clientId
    }

    internal object Injection {

        fun getTwitchService() =
                Retrofit.Builder()
                        .baseUrl(app.getString(R.string.twitch_api_base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()
                        .create(TwitchMediaService::class.java)!!
    }
}