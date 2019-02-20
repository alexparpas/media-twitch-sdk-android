package com.alexparpas.media.twitch.core

data class VideoBinding(
        val link: String,
        val title: String,
        val subtitle: String,
        val viewerCount: String,
        val thumbnailUrl: String,
        val duration: String
)