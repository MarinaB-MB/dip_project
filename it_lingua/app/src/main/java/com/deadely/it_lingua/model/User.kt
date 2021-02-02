package com.deadely.it_lingua.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.deadely.it_lingua.utils.USERS
import com.deadely.it_lingua.utils.USER_ID
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = USERS)
@Parcelize
data class User(
    @PrimaryKey
    @ColumnInfo(name = USER_ID)
    @SerializedName("_id") val id: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("stats") var stats: List<Stat?>?,
    @SerializedName("active") var active: Boolean,
    @SerializedName("name") val name: String
) : Parcelable

@Parcelize
data class UserBody(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("active") val active: Boolean,
    @SerializedName("name") val name: String,
    @SerializedName("stats") val stats: List<Stat?>?
) : Parcelable
