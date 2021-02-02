package com.deadely.it_lingua.ui.main

import com.deadely.it_lingua.base.BasePresenter
import com.deadely.it_lingua.navigation.Screens
import com.deadely.it_lingua.repository.Repository
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
    private val repository: Repository
) : BasePresenter<MainView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.initView()
    }

    fun openHomeScreen() {
        router.replaceScreen(Screens.HOME_SCREEN())
    }

    fun openLessonsScreen() {
        router.replaceScreen(Screens.LESSONS_SCREEN())
    }

    fun openTestsScreen() {
        router.replaceScreen(Screens.TESTS_SCREEN())
    }

    fun openDictionaryScreen() {
        router.replaceScreen(Screens.DICTIONARY_SCREEN())
    }

    fun exit() {
        router.exit()
    }
}
