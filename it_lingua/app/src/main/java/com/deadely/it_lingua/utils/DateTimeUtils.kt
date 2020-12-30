package com.deadely.it_lingua.utils

import java.text.SimpleDateFormat
import java.util.*

private val locale = Locale("RU", "ru")

fun formatWithPattern(date: Date): String {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", locale)
    return simpleDateFormat.format(date)
}