package com.deadely.it_lingua.repository

import com.deadely.it_lingua.database.dao.DictionaryDao
import com.deadely.it_lingua.database.dao.UserDao
import com.deadely.it_lingua.model.*
import com.deadely.it_lingua.network.RestService
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: RestService,
    private val ud: UserDao,
    private val dd: DictionaryDao
) {

    /* fun getUserById(id: String): Single<User> {
         return api.getUserById(id)
     }*/
    fun getUserById(id: String): Single<User> {
        return Single.just(
            User(
                id = "100x",
                email = "email@mail.ru",
                password = "123456",
                listOf(
                    Stat(
                        id = "1",
                        date = "12.04.2021",
                        countLessons = 1,
                        countTests = 1
                    ),
                    Stat(
                        id = "1",
                        date = "13.04.2021",
                        countLessons = 2,
                        countTests = 1
                    ),
                    Stat(
                        id = "1",
                        date = "14.04.2021",
                        countLessons = 2,
                        countTests = 2
                    ),
                    Stat(
                        id = "1",
                        date = "18.04.2021",
                        countLessons = 3,
                        countTests = 2
                    )

                ),
                active = true,
                name = "namel"
            )
        )
    }

    fun getUserByEmail(email: String): Single<List<User>> {
        return api.getUserByEmail("{\"email\":\"$email\"}")
    }

    fun deleteUser(id: String): Single<Completable> {
        return api.deleteUserById(id)
    }

    fun updateUser(put: User): Single<User> {
        return api.updateUser(put.id, put)
    }

    fun createUser(requestBody: UserBody): Single<User> {
        return api.createUser(requestBody)
    }

    fun getActiveUser() = ud.getActiveUser()

    fun saveActiveUser(user: User) {
        ud.clearActiveUser()
        ud.setActiveUser(user)
    }

    fun getLessons(): Single<List<Lesson>> = api.getLessons()

    fun getWords(): Single<List<Word>> = api.getWords()

    fun getFavoritesWords(): Single<List<Word>> = dd.getWords()

    fun addToFavoriteWords(word: Word) = dd.addWord(word)

    fun deleteFromFavoritesWords(word: Word) {
        return dd.deleteWord(word)
    }

    fun getTests(): Single<List<Test>> = api.getTests()

    fun createStat(stat: StatCreateBody): Single<Stat> = api.createStat(stat)

    fun updateStat(id: String, stat: StatCreateBody) = api.updateStat(id, stat)

    fun getStatByDate(date: String): Stat? {
        return getActiveUser()?.stats?.find { it?.date == date.plus("Z") }
    }

    fun clearActiveUser() {
        ud.clearActiveUser()
        dd.clearWords()
    }
}
