package com.deadely.it_lingua.repository

import com.deadely.it_lingua.database.dao.DictionaryDao
import com.deadely.it_lingua.database.dao.UserDao
import com.deadely.it_lingua.model.*
import com.deadely.it_lingua.network.RestService
import io.reactivex.Single
import org.json.JSONObject
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: RestService,
    private val ud: UserDao,
    private val dd: DictionaryDao
) {

    // api

    fun getUserById(id: String): Single<User> {
        return api.getUserById(id)
    }

    fun getUserByEmail(email: String): Single<List<User>> {
        return api.getUserByEmail("{\"email\":\"$email\"}")
    }

    fun deleteUser(id: String): User {
        return api.deleteUserById(id)
    }

    fun updateUser(id: String, put: User): Single<User> {
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

    fun getLessons(): Single<List<Lesson>> = api.getLessons()
    fun getWords(): Single<List<Word>> = api.getWords()

    fun addToFavoriteWords(word: Word) = dd.addWord(word)

    fun deleteFromFavoritesWords(word: Word) {
        return dd.deleteWord(word)
    }

    fun getTests(): Single<List<Test>> = api.getTests()

    fun getFavoritesWords(): Single<List<Word>> = dd.getWords()

//    fun updateUsersStat(id: String, stats: JSONObject): Single<User> = api.updateUsersStat(id, stats

    fun createStat(stat: StatCreateBody): Single<Stat> = api.createStat(stat)
}
