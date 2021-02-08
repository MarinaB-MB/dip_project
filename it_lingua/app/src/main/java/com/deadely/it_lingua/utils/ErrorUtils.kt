package com.deadely.it_lingua.utils

import com.deadely.it_lingua.App
import com.deadely.it_lingua.R
import com.deadely.it_lingua.model.neterror.ServerException
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import timber.log.Timber
import java.net.UnknownHostException

object ErrorUtils {
    private val gson = GsonBuilder().create()

    fun proceed(t: Throwable, body: (message: String) -> Unit = {}) {
        Timber.tag("ErrorUtils.Throwable: ").e(t.message.toString())
        when (t) {
            is HttpException -> {
                val e = parseError(t)
                val errorMessage =
                    "${e?.list?.first()?.message?.get(0)} ${e?.list?.first()?.field}"
                e?.let { body(errorMessage) }
            }
            is UnknownHostException -> body(App.instance.getString(R.string.internet_connection_error))
            else -> body(App.instance.getString(R.string.unexpected_error))
        }
    }

    private fun parseError(error: Throwable): ServerException? {
        if (error is HttpException) {
            val responseString = error.response()?.errorBody()?.string()
            if (!responseString.isNullOrEmpty()) {
                return try {
                    gson.fromJson(responseString, ServerException::class.java)
                } catch (e: JsonSyntaxException) {
                    null
                }
            }
        }
        return null
    }
}

/*
{
    "message": "Unable to save record (validation)",
    "name": "ValidationError",
    "list": [
    {
        "field": "email",
        "message": [
        "Missing required field",
        "REQUIRED"
        ]
    },
    {
        "field": "active",
        "message": [
        "Missing required field",
        "REQUIRED"
        ]
    }
    ],
    "status": 400
}*/

/*
{
    "message": "Nothing was updated. Check Query.",
    "name": "Error"
}*/
/*
{
    "message": "Unable to save record (validation)",
    "name": "ValidationError",
    "list": [
    {
        "field": "active",
        "message": [
        "Missing required field",
        "REQUIRED"
        ]
    }
    ],
    "status": 400
}*/
