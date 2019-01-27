package com.alexparpas.media.twitch.data

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

internal interface TwitchMediaService {
    @GET("helix/streams")
    fun getStreams(
            @Header("Client-Id") clientId: String,
            @Query("game_id") gameId: String? = null,
            @Query("name") name: String? = null
    ): Single<GameStreamResponse>
}