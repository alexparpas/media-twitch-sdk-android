package com.alexparpas.media.twitch.core.data

import com.alexparpas.media.twitch.core.data.model.Clip
import com.alexparpas.media.twitch.core.data.model.Stream
import com.alexparpas.media.twitch.core.data.model.Video
import com.alexparpas.media.twitch.core.data.model.VideoItem
import com.alexparpas.media.twitch.core.utils.TimeFormatter
import java.text.DecimalFormat

class TwitchMapper {

    fun mapClips(clips: List<Clip>) =
            clips.map {
                map(it)
            }

    fun mapVideos(videos: List<Video>) =
            videos.map {
                map(it)
            }

    fun mapStreams(streams: List<Stream>): List<VideoItem> =
            streams.map {
                map(it)
            }


    private fun map(clip: Clip): VideoItem {
        return with(clip) {
            val clippedAt = TimeFormatter.format(createdAt, "yyyy-MM-dd'T'HH:mm:ss'Z'", "EEE d/M/yy")

            VideoItem(
                    link = embedURL,
                    title = title,
                    subtitle = creatorName,
                    viewerCount = map(viewCount.toDouble()),
                    thumbnailUrl = thumbnailURL,
                    isLive = false,
                    duration = clippedAt
            )
        }
    }

    private fun map(stream: Stream): VideoItem {
        with(stream) {
            val thumbnailUrl = this.thumbnailUrl
                    ?.replace("{width}", "320")
                    ?.replace("{height}", "180")

            val link = "https://player.twitch.tv/?channel=${userName.orEmpty()}"

            return VideoItem(
                    link = link,
                    title = userName.orEmpty(),
                    subtitle = title.orEmpty(),
                    viewerCount = map(viewerCount?.toDouble()),
                    thumbnailUrl = thumbnailUrl.orEmpty(),
                    duration = null,
                    isLive = true
            )
        }
    }

    private fun map(video: Video): VideoItem {
        with(video) {
            val thumbnailUrl = thumbnailURL
                    .replace("%{width}", "320")
                    .replace("%{height}", "180")

            val durationPatterns: Pair<String, String> = TimeFormatter.getPatterns(duration)

            return VideoItem(
                    link = url,
                    title = title,
                    subtitle = userName,
                    viewerCount = map(viewCount.toDouble()),
                    thumbnailUrl = thumbnailUrl,
                    isLive = false,
                    duration = TimeFormatter.format(time = duration, inputPattern = durationPatterns.first, outputPattern = durationPatterns.second)
            )
        }
    }

    fun map(viewCount: Double?): String {
        return if (viewCount != null) {
            if (viewCount < 1000) return "${viewCount.toInt()} views"
            val exp = (Math.log(viewCount) / Math.log(1000.0)).toInt()
            val format = DecimalFormat("0.#")
            val value = format.format(viewCount / Math.pow(1000.0, exp.toDouble()))
            return String.format("%s%c", value, "kMBTPE"[exp - 1]) + " views"
        } else {
            ""
        }
    }
}