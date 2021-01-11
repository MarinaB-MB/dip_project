package com.deadely.it_lingua.ui.main

import com.deadely.it_lingua.base.BasePresenter
import com.deadely.it_lingua.navigation.Screens
import com.deadely.it_lingua.repository.Repository
import moxy.InjectViewState
import javax.inject.Inject
@InjectViewState
class MainPresenter @Inject constructor(private val repository: Repository) : BasePresenter<MainView>() {
    fun openHomeScreen() {
        router.replaceScreen(Screens.getScreenByKey(Screens.HOME_SCREEN))
    }

    fun openLessonsScreen() {
        router.replaceScreen(Screens.getScreenByKey(Screens.LESSONS_SCREEN))
    }

    fun openTestsScreen() {
        router.replaceScreen(Screens.getScreenByKey(Screens.TESTS_SCREEN))
    }

    fun openDictionaryScreen() {
        router.replaceScreen(Screens.getScreenByKey(Screens.DICTIONARY_SCREEN))
    }

    fun exit() {
        router.exit()
    }
}
