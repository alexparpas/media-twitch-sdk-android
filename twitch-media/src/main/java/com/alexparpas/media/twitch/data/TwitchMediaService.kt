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
    ): Single<StreamsResponse>

    @GET("helix/clips")
    fun getClips(
            @Header("Client-Id") clientId: String,
            @Query("game_id") gameId: String
    ): Single<GameClipsResponse>

    @GET("helix/videos")
    fun getVideos(
            @Header("Client-Id") clientId: String,
            @Query("game_id") gameId: String? = null,
            @Query("id") ids: String? = null
    ): Single<GameVideosResponse>
}