package com.alexparpas.media.twitch.data

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

fun Stream.toVideoBinding(): VideoBinding {
    val thumbnailUrl = this.thumbnailUrl
            ?.replace("{width}", "320")
            ?.replace("{height}", "180")

    val link = "https://player.twitch.tv/?channel=${userName.orEmpty()}"

    return VideoBinding(
            link = link,
            title = userName.orEmpty(),
            subtitle = title.orEmpty(),
            viewerCount = viewerCount?.toString().orEmpty(),
            thumbnailUrl = thumbnailUrl.orEmpty(),
            duration = startedAt.orEmpty()
    )
}