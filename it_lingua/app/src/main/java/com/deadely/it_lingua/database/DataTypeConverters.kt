package com.deadely.it_lingua.database

import androidx.room.TypeConverter
import com.deadely.it_lingua.model.Stat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable
import java.util.*

class StatsEntityTypeConverter : Serializable {
    val gson = Gson()

    @TypeConverter
    fun stringToStatsEntityList(data: String?): List<Stat> {
        data?.let {
            val listType = object : TypeToken<List<Stat>>() {}.type
            return gson.fromJson(data, listType)
        } ?: let { return Collections.emptyList() }
    }

    @TypeConverter
    fun statsEntityListToString(list: List<Stat>): String {
        return gson.toJson(list)
    }
}

class DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}