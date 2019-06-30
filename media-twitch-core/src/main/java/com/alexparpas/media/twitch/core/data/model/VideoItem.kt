package com.alexparpas.media.twitch.core.data.model

data class VideoItem(
        val link: String,
        val title: String,
        val subtitle: String,
        val viewerCount: String,
        val thumbnailUrl: String,
        val isLive: Boolean,
        val duration: String?
) {
    val shouldShowDuration: Boolean get() = !isLive
}