package com.deadely.it_lingua.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Word(
    val id: Int,
    val text: String,
    val translate: String,
    val transcription: String,
    @Transient
    var isFavorite: Boolean = false
) : Parcelable

fun getWords() = listOf(
    Word(0, "aaa", "aaa", "aaa"),
    Word(1, "иии", "иии", "иии"),
    Word(2, "ппп", "ппп", "ппп"),
    Word(3, "ссс", "ссс", "ссс"),
    Word(4, "ттт", "ттт", "ттт"),
    Word(5, "aaфффa", "aaaаыывп", "aврафварг aa", true)
)
