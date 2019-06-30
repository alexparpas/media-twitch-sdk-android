package com.alexparpas.media.twitch.core.data

import com.alexparpas.media.twitch.core.data.model.Clip
import com.alexparpas.media.twitch.core.data.model.Stream
import com.alexparpas.media.twitch.core.data.model.Video

class TwitchMediaLocalStorage {
    var streams: List<Stream> = listOf()
    var clips: List<Clip> = listOf()
    var videos: List<Video> = listOf()
}