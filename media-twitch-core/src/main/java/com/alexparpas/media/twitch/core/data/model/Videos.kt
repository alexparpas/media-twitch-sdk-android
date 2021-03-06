package com.alexparpas.media.twitch.core.data.model

import com.google.gson.annotations.SerializedName

data class GameVideosResponse(val data: List<Video>)

data class Video(
        val id: String,
        @SerializedName("user_id") val userID: String,
        @SerializedName("user_name") val userName: String,
        val title: String,
        val description: String,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("published_at")
        val publishedAt: String,
        val url: String,
        @SerializedName("thumbnail_url") val thumbnailURL: String,
        val viewable: String,
        @SerializedName("view_count") val viewCount: Long,
        val language: String,
        val type: String,
        val duration: String
)