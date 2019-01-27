package com.alexparpas.media.twitch.data

import io.reactivex.Single

class TwitchMediaRepository internal constructor(private val service: TwitchMediaService) {

    fun getStreamsById(clientId: String, gameId: String): Single<List<GameStream>> =
            service.getStreams(clientId = clientId, gameId = gameId).map { it -> it.data }

    fun getStreamsByName(clientId: String, name: String): Single<List<GameStream>> =
            service.getStreams(clientId = clientId, name = name).map { it -> it.data }
}
