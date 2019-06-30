package com.alexparpas.media.twitch.core.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeFormatter {

    //TODO See if there's a better way to do this
    fun getPatterns(value: String): Pair<String, String> {
        return when {
            value.contains("h") -> "HH'h'mm'm'ss's'" to "HH:mm:ss"
            value.contains("m") -> "mm'm'ss's'" to "mm:ss"
            value.contains("s") -> "ss's'" to "00:ss"
            else -> "" to ""
        }
    }

    fun format(time: String, inputPattern: String, outputPattern: String): String? {
        return try {
            val date: Date = SimpleDateFormat(inputPattern, Locale.getDefault()).parse(time)
            SimpleDateFormat(outputPattern, Locale.getDefault()).format(date)
        } catch (e: Exception) {
            ""
        }
    }
}