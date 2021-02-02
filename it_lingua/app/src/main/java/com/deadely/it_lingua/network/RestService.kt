package com.deadely.it_lingua.network

import com.deadely.it_lingua.model.*
import com.deadely.it_lingua.utils.*
import io.reactivex.Single
import org.json.JSONObject
import retrofit2.http.*

interface RestService {
    @GET(GET_USER)
    fun getUserById(@Path(ID) id: String): Single<User>

    @GET(GET_USER_BY_EMAIL)
    fun getUserByEmail(@Query(QUERY) query: String): Single<List<User>>

    @DELETE(DELETE_USER)
    fun deleteUserById(@Path(ID) id: String): User

    @POST(CREATE_USER)
    fun createUser(@Body requestBody: UserBody): Single<User>

    @PUT(UPDATE_USER)
    fun updateUser(
        @Path(OBJECT_ID) id: String,
        @Body put: User
    ): Single<User>

    @PUT(UPDATE_USER)
    fun updateUsersStat(
        @Path(OBJECT_ID) id: String,
        @Body stats: JSONObject
    ): Single<User>

    @GET(GET_WORDS)
    fun getWords(): Single<List<Word>>

    @GET(GET_LESSONS)
    fun getLessons(): Single<List<Lesson>>

    @GET(GET_TESTS)
    fun getTests(): Single<List<Test>>

    @POST(CREATE_STAT)
    fun createStat(@Body stat: StatCreateBody): Single<Stat>
}
