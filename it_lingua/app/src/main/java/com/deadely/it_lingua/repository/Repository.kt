package com.deadely.it_lingua.repository

import com.deadely.it_lingua.database.dao.DictionaryDao
import com.deadely.it_lingua.database.dao.UserDao
import com.deadely.it_lingua.model.*
import com.deadely.it_lingua.network.RestService
import io.reactivex.Single
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: RestService,
    private val ud: UserDao,
    private val dd: DictionaryDao
) {

    // api

    fun getUserById(id: String): User {
        return api.getUserById(id)
    }

    fun getUserByEmail(email: String): Single<List<User>> {
        return api.getUserByEmail("{\"email\":\"$email\"}")
    }

    fun deleteUser(id: String): User {
        return api.deleteUserById(id)
    }

    fun updateUser(id: String, put: User): User {
        return api.updateUser(id, put)
    }

    fun createUser(requestBody: UserBody): Single<User> {
        return api.createUser(requestBody)
    }

    // ud

    fun clearActiveUser() {
        ud.clearActiveUser()
    }

    fun getActiveUser() = ud.getActiveUser()
    fun saveActiveUser(user: User) {
        ud.clearActiveUser()
        ud.setActiveUser(user)
    }

    fun getLessons(): Single<List<Lesson>> {
        return api.getLessons()
    }

    fun getWords(): Single<List<Word>> {
        return api.getWords()
    }

    fun addToFavoriteWords(word: Word) {
        return dd.addWord(word)
    }

    fun deleteFromFavoritesWords(word: Word) {
        return dd.deleteWord(word)
    }

    fun getTests(): Single<List<Test>> {
        return api.getTests()
    }

    fun getFavoritesWords(): Single<List<Word>> {
        return dd.getWords()
    }
}
