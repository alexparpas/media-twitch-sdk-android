package com.alexparpas.media.twitch.data

data class VideoBinding(
        val urlSuffix: String,
        val title: String,
        val subtitle: String,
        val viewerCount: String,
        val thumbnailUrl: String,
        val duration: String
)