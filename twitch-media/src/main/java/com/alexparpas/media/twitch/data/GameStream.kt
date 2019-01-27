package com.alexparpas.media.twitch.data

import com.google.gson.annotations.SerializedName

data class GameStreamResponse(val data: List<GameStream>)

data class GameStream(
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

data class GameStreamMinimal(
        val userId: String,
        val userName: String,
        val viewerCount: Long,
        val startedAt: String,
        val thumbnailUrl: String
)

fun GameStream.toMinimal(): GameStreamMinimal {
    return GameStreamMinimal(
            userId.orEmpty(),
            userName.orEmpty(),
            viewerCount ?: 0,
            startedAt.orEmpty(),
            thumbnailUrl.orEmpty()
    )
}