package com.deadely.it_lingua.ui.dictionary

import com.deadely.it_lingua.App
import com.deadely.it_lingua.R
import com.deadely.it_lingua.base.BasePresenter
import com.deadely.it_lingua.model.Word
import com.deadely.it_lingua.navigation.Screens
import com.deadely.it_lingua.repository.Repository
import com.deadely.it_lingua.utils.ErrorUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class DictionaryPresenter @Inject constructor(
    private val repository: Repository
) :
    BasePresenter<DictionaryView>() {
    private var apiList = mutableListOf<Word>()
    private var localList = listOf<Word>()

    override fun onFirstViewAttach() {
        getApiWords()
    }

    private fun getApiWords() {
        viewState.showProgress(true)
        repository.getWords().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { data ->
                    apiList = data.toMutableList()
                    getLocalList(true)
                },
                { e -> ErrorUtils.proceed(e) { viewState.showError(it) } }
            )
    }

    private fun getLocalList(isRefresh: Boolean) {
        repository.getFavoritesWords().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { data ->
                    localList = data
                    if (isRefresh) {
                        mergeLists()
                    } else {
                        viewState.setWordList(localList)
                    }
                },
                { e -> ErrorUtils.proceed(e) { viewState.showError(it) } }
            )
    }

    fun exit() {
        router.replaceScreen(Screens.HOME_SCREEN())
    }

    fun addFavorite(word: Word) {
        repository.addToFavoriteWords(word)
        repository.getFavoritesWords().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                { data ->
                    if (data.contains(word)) {
                        viewState.showError(App.instance.getString(R.string.saved_successfully))
                    } else {
                        viewState.showError(App.instance.getString(R.string.saved_error))
                    }
                },
                { e -> ErrorUtils.proceed(e) { viewState.showError(it) } }
            )
    }

    fun deleteFavorites(word: Word) {
        repository.deleteFromFavoritesWords(word)
        repository.getFavoritesWords().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                { data ->
                    if (!data.contains(word)) {
                        viewState.showError(App.instance.getString(R.string.deleting_successfully))
                    } else {
                        viewState.showError(App.instance.getString(R.string.deleting_error))
                    }
                },
                { e -> ErrorUtils.proceed(e) { viewState.showError(it) } }
            )
    }

    private fun mergeLists() {
        localList.forEach {
            it.isFavorite = !it.isFavorite
            if (apiList.contains(it)) {
                val index = apiList.indexOf(it)
                apiList.remove(it)
                it.isFavorite = !it.isFavorite
                apiList.add(index, it)
            }
        }
        viewState.setWordList(apiList)
        viewState.showProgress(false)
    }

    fun getList() {
        getApiWords()
    }

    fun searchQuery(query: String?) {
        viewState.clearList()
        query?.let {
            var filteredList = listOf<Word>()
            if (query.isNotEmpty()) {
                filteredList = apiList.filter {
                    it.text.contains(query, ignoreCase = true)
                }
                viewState.setWordList(filteredList)
            } else {
                viewState.setWordList(apiList)
            }
        }
    }

    fun onFavoritesChanged(isChecked: Boolean) {
        if (isChecked) {
            getLocalList(false)
        } else {
            viewState.setWordList(apiList)
        }
    }
}
