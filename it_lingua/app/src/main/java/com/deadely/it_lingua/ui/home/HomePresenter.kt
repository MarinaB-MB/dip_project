package com.deadely.it_lingua.ui.home

import android.content.Context
import com.deadely.it_lingua.base.BasePresenter
import com.deadely.it_lingua.model.User
import com.deadely.it_lingua.navigation.Screens
import com.deadely.it_lingua.navigation.Screens.ACCOUNT_SCREEN
import com.deadely.it_lingua.repository.Repository
import com.deadely.it_lingua.utils.ACCOUNT_RESULT
import com.deadely.it_lingua.utils.ErrorUtils
import com.deadely.it_lingua.utils.subscribeAndObserve
import dagger.hilt.android.qualifiers.ApplicationContext
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class HomePresenter @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: Repository
) :
    BasePresenter<HomeView>() {
    private var graphMode = ""
    private var activeUser: User? = null

    override fun onFirstViewAttach() {
        viewState.initView()
        setResultListener()
        updateUserData()
    }

    private fun setResultListener() {
        router.setResultListener(ACCOUNT_RESULT) {
            if ((it as Int) == 1) {
                repository.clearActiveUser()
                router.apply {
                    finishChain()
                    newRootScreen(Screens.REGISTRATION_SCREEN())
                }
            }
        }
    }

    private fun updateUserData() {
        viewState.showProgress(true)
        val userId = repository.getActiveUser()?.id ?: ""
        repository.getUserById(userId).subscribeAndObserve().doFinally {
            viewState.showProgress(false)
        }
            .subscribe(
                { user ->
                    repository.saveActiveUser(user)
                    activeUser = user
                    viewState.setUserData(user)
                },
                { error -> ErrorUtils.proceed(error) { viewState.showError(it) } }
            )
    }

    fun exit() {
        router.exit()
    }

    fun openAccountScreen() {
        router.navigateTo(ACCOUNT_SCREEN())
    }

    fun changeGraphMode(mode: String) {
        graphMode = mode
        when (graphMode) {
            HomeFragment.TEST_MODE -> viewState.setUserData(activeUser, graphMode)
            HomeFragment.LESSON_MODE -> viewState.setUserData(activeUser, graphMode)
        }
    }
}
