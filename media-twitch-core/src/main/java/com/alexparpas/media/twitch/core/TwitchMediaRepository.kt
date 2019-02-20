package com.alexparpas.media.twitch.core

import io.reactivex.Single

class TwitchMediaRepository internal constructor(private val service: TwitchMediaService) {

    fun getLiveStreamsById(clientId: String, gameId: String, first: Int = 50): Single<List<Stream>> =
            service.getStreams(clientId = clientId, gameId = gameId).map { it.data }

    fun getLiveStreamsByName(clientId: String, name: String, first: Int = 50): Single<List<Stream>> =
            service.getStreams(clientId = clientId, name = name).map { it.data }

    fun getClips(clientId: String, gameId: String, first: Int = 50): Single<List<Clip>> =
            service.getClips(clientId = clientId, gameId = gameId).map { it.data }

    fun getVideosByGame(clientId: String, gameId: String, first: Int = 50): Single<List<Video>> =
            service.getVideos(clientId = clientId, gameId = gameId).map { it.data }

    fun getVideosByIds(clientId: String, ids: List<String>, first: Int = 50): Single<List<Video>> =
            service.getVideos(clientId = clientId, ids = ids.joinToString(",")).map { it.data }
}
