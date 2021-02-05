package com.deadely.it_lingua.utils

import java.text.SimpleDateFormat
import java.util.*

val locale = Locale("RU", "ru")
const val PATTERN_1 = "yyyy-MM-dd"
const val PATTERN_2 = "dd MMM"
const val PATTERN_3 = "yyyy-MM-dd'T'HH:mm:ss.000"

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

fun getCurrentDateWithoutTime(): String {
    val date = Calendar.getInstance()
    date.set(
        date.get(Calendar.YEAR),
        date.get(Calendar.MONTH),
        date.get(Calendar.DAY_OF_MONTH), 0, 0, 0
    )
    return formatWithPattern(date.time, PATTERN_3)
}
