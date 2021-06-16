package com.deadely.it_lingua

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.deadely.it_lingua.database.AppDatabase
import com.deadely.it_lingua.database.dao.DictionaryDao
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class DictionaryDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var dao: DictionaryDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.dictionaryDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun getData() {
    }
}
