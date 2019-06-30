package com.alexparpas.media.twitch.core.data.model

import com.google.gson.annotations.SerializedName

data class StreamsResponse(val data: List<Stream>)

data class Stream(
        val id: String?,
        @SerializedName("user_id") val userId: String?,
        @SerializedName("user_name") val userName: String?,
        @SerializedName("game_id") val gameId: String?,
        @SerializedName("community_ids") val communityIds: List<String>?,
        val type: String?,
        val title: String?,
        @SerializedName("viewer_count") val viewerCount: Long?,
        @SerializedName("started_at") val startedAt: String?,
        val language: String?,
        @SerializedName("thumbnail_url") val thumbnailUrl: String?,
        @SerializedName("tag_ids") val tagIds: List<String>?
)