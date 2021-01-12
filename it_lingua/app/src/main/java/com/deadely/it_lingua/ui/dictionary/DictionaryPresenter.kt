package com.deadely.it_lingua.ui.dictionary

import com.deadely.it_lingua.base.BasePresenter
import com.deadely.it_lingua.model.Word
import com.deadely.it_lingua.model.getWords
import com.deadely.it_lingua.navigation.Screens
import com.deadely.it_lingua.repository.Repository
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class DictionaryPresenter @Inject constructor(private val repository: Repository) :
    BasePresenter<DictionaryView>() {

    override fun onFirstViewAttach() {
        viewState.setWordList(getWords())
    }

    fun exit() {
        router.replaceScreen(Screens.getScreenByKey(Screens.HOME_SCREEN))
    }

    fun addFavorite(word: Word) {
        repository.addToFavoriteWords(word)
        // getUpdatedList()
    }

    fun deleteFavorites(word: Word) {
        repository.deleteFromFavoritesWords(word)
        // getUpdatedList()
    }

    fun getList() {
        viewState.setWordList(getWords())
    }

    fun searchQuery(query: String?) {
        viewState.clearList()
        query?.let {
            var filteredList = listOf<Word>()
            if (query.isNotEmpty()) {
                filteredList = getWords().filter {
                    it.text.contains(query, ignoreCase = true)
                }
                viewState.setWordList(filteredList)
            } else {
                viewState.setWordList(getWords())
            }
        }
    }

    fun onFavoritesChanged(isChecked: Boolean) {
        if (isChecked) {
            val filteredList = getWords().filter { it.isFavorite }
            viewState.setWordList(filteredList)
        } else {
            viewState.setWordList(getWords())
        }

    }
}
