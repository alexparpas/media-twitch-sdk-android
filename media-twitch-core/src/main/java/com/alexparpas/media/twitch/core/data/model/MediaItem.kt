package com.alexparpas.media.twitch.core.data.model

sealed class MediaItem
class CategoryItem(val categoryName: String, val mediaType: MediaType) : MediaItem()
class MediaBindingItem(val videos: List<VideoItem>) : MediaItem()