package com.deadely.it_lingua.ui.splash

import com.deadely.it_lingua.base.BasePresenter
import com.deadely.it_lingua.database.dao.UserDao
import com.deadely.it_lingua.navigation.Screens
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class SplashPresenter @Inject constructor(private val ud: UserDao) : BasePresenter<SplashView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.initView(isUserActive())
    }

    private fun isUserActive() = ud.getActiveUser()?.active ?: run { false }

    fun openMainScreen() {
        router.newRootScreen(Screens.MAIN_SCREEN())
    }

    fun openRegScreen() {
        router.newRootScreen(Screens.REGISTRATION_SCREEN())
    }
}
