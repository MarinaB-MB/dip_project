package com.deadely.it_lingua.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.deadely.it_lingua.database.dao.DictionaryDao
import com.deadely.it_lingua.database.dao.UserDao
import com.deadely.it_lingua.model.User
import com.deadely.it_lingua.model.Word
import com.deadely.it_lingua.utils.ITL

@Database(
    entities = [User::class, Word::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class, StatsEntityTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = ITL
    }

    abstract fun userDao(): UserDao
    abstract fun dictionaryDao(): DictionaryDao
}
