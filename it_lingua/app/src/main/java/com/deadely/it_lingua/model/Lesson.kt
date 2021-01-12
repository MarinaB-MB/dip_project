package com.deadely.it_lingua.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Lesson(
    val id: Int,
    val title: String,
    val content: String,
    @Transient
    val isChecked: Boolean = false
) : Parcelable

fun getLessons() = listOf(
    Lesson(
        0, "Lesson 1",
        "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<body>\n" +
            "\n" +
            "<h1>My First Heading</h1>\n" +
            "\n" +
            "<img src=\"https://2ch.hk/news/src/5042798/15565227027430.jpg\" alt=\"help\"\n/>" +
            "<p>My first paragraph.</p>\n" +
            "\n" +
            "</body>\n" +
            "</html>\n",
        true
    ),
    Lesson(1, "Lesson 2", ""),
    Lesson(2, "Lesson 3", ""),
    Lesson(3, "Lesson 4", ""),
    Lesson(4, "Lesson 5", ""),
    Lesson(5, "Lesson 6", ""),
)
