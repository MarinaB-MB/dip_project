package com.deadely.it_lingua.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.deadely.it_lingua.utils.DICTIONARY
import com.deadely.it_lingua.utils.DICTIONARY_ID
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = DICTIONARY)
@Parcelize
data class Word(
    @PrimaryKey
    @ColumnInfo(name = DICTIONARY_ID)
    @SerializedName("_id") val id: String,
    @SerializedName("word") val text: String,
    @SerializedName("translate") val translate: String,
    @SerializedName("tr") val transcription: String,
    var isFavorite: Boolean = false
) : Parcelable