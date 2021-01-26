package com.deadely.it_lingua.model.neterror

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ServerException(
    val message: String,
    val name: String,
    val list: List<ServerErrorDetail>,
    val status: Int
) : Parcelable

@Parcelize
data class ServerErrorDetail(
    val field: String,
    val message: List<String>
) : Parcelable