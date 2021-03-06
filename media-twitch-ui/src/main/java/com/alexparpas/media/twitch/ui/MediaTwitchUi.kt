package com.alexparpas.media.twitch.ui

import android.content.Context
import android.content.Intent
import com.alexparpas.media.twitch.core.MediaTwitch
import com.alexparpas.media.twitch.core.data.TwitchMapper
import com.alexparpas.media.twitch.core.data.model.MediaType
import com.alexparpas.media.twitch.ui.media.main.TwitchMainMediaFragment
import com.alexparpas.media.twitch.ui.media.main.TwitchMainMediaViewModelFactory
import com.alexparpas.media.twitch.ui.media.more.TwitchMediaMoreFragment
import com.alexparpas.media.twitch.ui.media.more.TwitchMoreViewModelFactory
import com.alexparpas.media.twitch.ui.video.TwitchPlayerActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

object MediaTwitchUi {
    private val videoPlayedSubject: PublishSubject<String> = PublishSubject.create()
    internal const val ARG_LINK = "ARG_LINK"
    internal const val ARG_GAME_ID = "ARG_GAME_ID"

    fun getTwitchMediaFragment(gameId: String): TwitchMainMediaFragment =
            TwitchMainMediaFragment.newInstance(gameId)

    fun getTwitchMediaMoreFragment(gameId: String, categoryName: String, mediaType: MediaType): TwitchMediaMoreFragment =
            TwitchMediaMoreFragment.newInstance(gameId, categoryName, mediaType)

    fun playVideo(context: Context, url: String) {
        context.startActivity(
                Intent(context, TwitchPlayerActivity::class.java).apply {
                    putExtra(ARG_LINK, url)
                }
        )
        videoPlayedSubject.onNext(url)
    }

    fun onVideoPlayed(): Observable<String> = videoPlayedSubject

    internal object Injection {
        fun provideMediaViewModelFactory(gameId: String): TwitchMainMediaViewModelFactory =
                TwitchMainMediaViewModelFactory(
                        gameId = gameId,
                        ioScheduler = Schedulers.io(),
                        uiScheduler = AndroidSchedulers.mainThread(),
                        twitchMediaRepository = MediaTwitch.twitchMediaRepository,
                        mapper = TwitchMapper()
                )

        fun provideMediaMoreViewModelFactory(gameId: String, categoryName: String, mediaType: MediaType): TwitchMoreViewModelFactory =
                TwitchMoreViewModelFactory(
                        gameId = gameId,
                        categoryName = categoryName,
                        mediaType = mediaType,
                        ioScheduler = Schedulers.io(),
                        uiScheduler = AndroidSchedulers.mainThread(),
                        repository = MediaTwitch.twitchMediaRepository,
                        mapper = TwitchMapper()
                )
    }
}