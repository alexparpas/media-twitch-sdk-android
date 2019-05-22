package com.alexparpas.media.twitch.core

import io.reactivex.Single

class TwitchMediaRepository internal constructor(
        private val clientId: String,
        private val service: TwitchMediaService
) {

    fun getLiveStreamsById(gameId: String, first: Int = 50): Single<List<Stream>> =
            service.getStreams(clientId = clientId, gameId = gameId).map { it.data }

    fun getLiveStreamsByName(name: String, first: Int = 50): Single<List<Stream>> =
            service.getStreams(clientId = clientId, name = name).map { it.data }

    fun getClips(gameId: String, first: Int = 50): Single<List<Clip>> =
            service.getClips(clientId = clientId, gameId = gameId).map { it.data }

    fun getVideosByGame(gameId: String, first: Int = 50): Single<List<Video>> =
            service.getVideos(clientId = clientId, gameId = gameId).map { it.data }

    fun getVideosByIds(ids: List<String>, first: Int = 50): Single<List<Video>> =
            service.getVideos(clientId = clientId, ids = ids.joinToString(",")).map { it.data }
}

enum class MediaType {
    LIVE, VOD, CLIP
}