package com.deadely.it_lingua.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Test(
    @SerializedName("_id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("asks") val asks: List<Ask>,
    var isChecked: Boolean = false
) : Parcelable

@Parcelize
data class Ask(
    @SerializedName("_id") val id: String,
    @SerializedName("ask") val ask: String = "",
    @SerializedName("image") val image: String = "",
    @SerializedName("is_svg") val isSvg: Boolean = false,
    @SerializedName("number") val number: Int = -1,
    @SerializedName("answers") val answers: String = ""
) : Parcelable
