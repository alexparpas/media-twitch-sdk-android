package com.alexparpas.media.twitch.core

sealed class MediaItem
class CategoryItem(val categoryName: String) : MediaItem()
class MediaBindingItem(val videos: List<VideoBinding>) : MediaItem()