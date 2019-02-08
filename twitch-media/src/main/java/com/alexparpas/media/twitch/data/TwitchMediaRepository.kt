package com.alexparpas.media.twitch.data

import io.reactivex.Single

class TwitchMediaRepository internal constructor(private val service: TwitchMediaService) {

    fun getStreamsById(clientId: String, gameId: String): Single<List<Stream>> =
            service.getStreams(clientId = clientId, gameId = gameId).map { it -> it.data }

    fun getStreamsByName(clientId: String, name: String): Single<List<Stream>> =
            service.getStreams(clientId = clientId, name = name).map { it -> it.data }

    fun getClips(clientId: String, gameId: String): Single<List<Clip>> =
            service.getClips(clientId = clientId, gameId = gameId).map { it -> it.data }

    fun getVideosByGame(clientId: String, gameId: String): Single<List<Video>> =
            service.getVideos(clientId = clientId, gameId = gameId).map { it -> it.data }

    fun getVideosByIds(clientId: String, ids: List<String>): Single<List<Video>> =
            service.getVideos(clientId = clientId, ids = ids.joinToString(",")).map { it -> it.data }
}
