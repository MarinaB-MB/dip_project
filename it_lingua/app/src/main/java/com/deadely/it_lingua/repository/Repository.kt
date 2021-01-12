package com.deadely.it_lingua.repository

import com.deadely.it_lingua.model.Word
import com.deadely.it_lingua.network.ApiService
import javax.inject.Inject


class Repository @Inject constructor(private val api: ApiService) {
    fun addToFavoriteWords(word: Word) {

    }

    fun deleteFromFavoritesWords(word: Word) {

    }

    fun searchWord(query: String) {

    }
}