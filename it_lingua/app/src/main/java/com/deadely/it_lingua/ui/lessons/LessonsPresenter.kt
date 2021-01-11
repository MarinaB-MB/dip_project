package com.deadely.it_lingua.ui.lessons

import com.deadely.it_lingua.base.BasePresenter
import com.deadely.it_lingua.navigation.Screens
import com.deadely.it_lingua.repository.Repository
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class LessonsPresenter @Inject constructor(private val repository: Repository) :
    BasePresenter<LessonsView>() {
    fun exit() {
        router.replaceScreen(Screens.getScreenByKey(Screens.HOME_SCREEN))
    }

}
