package com.deadely.it_lingua.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Lesson(
    @SerializedName("_id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    var isChecked: Boolean = false
) : Parcelable

