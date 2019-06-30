package com.alexparpas.media.twitch.core.data

import com.alexparpas.media.twitch.core.data.model.Clip
import com.alexparpas.media.twitch.core.data.model.Stream
import com.alexparpas.media.twitch.core.data.model.Video
import io.reactivex.Single

class TwitchMediaRepository internal constructor(
        private val clientId: String,
        private val service: TwitchMediaService,
        private val twitchMediaLocalStorage: TwitchMediaLocalStorage
) {

    fun getLiveStreamsById(gameId: String, first: Int = 50): Single<List<Stream>> {
        return twitchMediaLocalStorage.streams
                .takeIf { it.isNotEmpty() }
                ?.let { Single.just(it) }
                ?: service.getStreams(clientId = clientId, gameId = gameId, first = first)
                        .map { it.data }
                        .doOnSuccess { twitchMediaLocalStorage.streams = it }
    }

    fun getLiveStreamsByName(name: String, first: Int = 50): Single<List<Stream>> {
        return twitchMediaLocalStorage.streams
                .takeIf { it.isNotEmpty() }
                ?.let { Single.just(it) }
                ?: service.getStreams(clientId = clientId, name = name, first = first)
                        .map { it.data }
                        .doOnSuccess { twitchMediaLocalStorage.streams = it }
    }

    fun getClips(gameId: String, first: Int = 50): Single<List<Clip>> {
        return twitchMediaLocalStorage.clips
                .takeIf { it.isNotEmpty() }
                ?.let { Single.just(it) }
                ?: service.getClips(clientId = clientId, gameId = gameId, first = first)
                        .map { it.data }
                        .doOnSuccess { twitchMediaLocalStorage.clips = it }
    }

    fun getVideosByGame(gameId: String, first: Int = 50): Single<List<Video>> {
        return twitchMediaLocalStorage.videos
                .takeIf { it.isNotEmpty() }
                ?.let { Single.just(it) }
                ?: service.getVideos(clientId = clientId, gameId = gameId, first = first)
                        .map { it.data }
                        .doOnSuccess { twitchMediaLocalStorage.videos = it }
    }
}