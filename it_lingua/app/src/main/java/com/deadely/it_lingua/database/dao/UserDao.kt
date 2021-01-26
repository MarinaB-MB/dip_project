package com.deadely.it_lingua.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deadely.it_lingua.model.User
import com.deadely.it_lingua.utils.USERS

@Dao
interface UserDao {
    @Query("SELECT * FROM $USERS WHERE active = 1")
    fun getActiveUser(): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setActiveUser(userEntity: User)

    @Query("DELETE FROM $USERS")
    fun clearActiveUser()
}
