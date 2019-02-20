package com.alexparpas.media.twitch.core

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

internal interface TwitchMediaService {
    @GET("helix/streams")
    fun getStreams(
            @Header("Client-Id") clientId: String,
            @Query("game_id") gameId: String? = null,
            @Query("name") name: String? = null,
            @Query("first") first: Int = 50
    ): Single<StreamsResponse>

    @GET("helix/clips")
    fun getClips(
            @Header("Client-Id") clientId: String,
            @Query("game_id") gameId: String,
            @Query("first") first: Int = 50
    ): Single<GameClipsResponse>

    @GET("helix/videos")
    fun getVideos(
            @Header("Client-Id") clientId: String,
            @Query("game_id") gameId: String? = null,
            @Query("id") ids: String? = null,
            @Query("first") first: Int = 50
    ): Single<GameVideosResponse>
}