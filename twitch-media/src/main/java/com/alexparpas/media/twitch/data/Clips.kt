package com.alexparpas.media.twitch.data

import com.google.gson.annotations.SerializedName

data class GameClipsResponse(val data: List<GameClip>)

data class GameClip(
        val id: String,
        val url: String,
        @SerializedName("embed_url") val embedURL: String,
        @SerializedName("broadcaster_id") val broadcasterID: String,
        @SerializedName("broadcaster_name") val broadcasterName: String,
        @SerializedName("creator_id") val creatorID: String,
        @SerializedName("creator_name") val creatorName: String,
        @SerializedName("video_id") val videoID: String,
        @SerializedName("game_id") val gameID: String,
        val language: String,
        val title: String,
        @SerializedName("view_count") val viewCount: Long,
        @SerializedName("created_at") val createdAt: String,
        @SerializedName("thumbnail_url") val thumbnailURL: String
)