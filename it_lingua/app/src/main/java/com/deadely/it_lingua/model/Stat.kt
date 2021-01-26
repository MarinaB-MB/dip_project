package com.deadely.it_lingua.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.deadely.it_lingua.utils.STATS
import com.deadely.it_lingua.utils.STAT_ID
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Entity(tableName = STATS)
@Parcelize
data class Stat(
    @ColumnInfo(name = STAT_ID)
    @PrimaryKey
    @SerializedName("_id") val id: String,
    @SerializedName("date") val date: String,
    @SerializedName("count_tests") val countTests: Int,
    @SerializedName("count_lessons") val countLessons: Int,
) : Parcelable