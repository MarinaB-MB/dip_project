package com.deadely.it_lingua.utils

import java.text.SimpleDateFormat
import java.util.*

val locale = Locale("RU", "ru")
const val PATTERN_1 = "yyyy-MM-dd"
const val PATTERN_2 = "dd MMM"
const val PATTERN_3 = "yyyy-MM-dd'T'HH:mm:ss.SSS"

fun formatWithPattern(date: Date, pattern: String): String {
    val simpleDateFormat = SimpleDateFormat(pattern, locale)
    return simpleDateFormat.format(date)
}

fun formatLongToString(date: Long): String {
    return SimpleDateFormat(PATTERN_2, locale).format(Date(date))
}

fun formatString(date: String): Float {
    return try {
        SimpleDateFormat(PATTERN_3, locale).parse(date.replace("Z", "")).time.toFloat()
    } catch (e: Exception) {
        0F
    }
}
