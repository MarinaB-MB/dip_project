package com.deadely.it_lingua.ui.tests

import com.deadely.it_lingua.base.BasePresenter
import com.deadely.it_lingua.model.Test
import com.deadely.it_lingua.navigation.Screens
import com.deadely.it_lingua.navigation.Screens.TEST_DETAIL_SCREEN
import com.deadely.it_lingua.repository.Repository
import com.deadely.it_lingua.utils.ErrorUtils
import com.deadely.it_lingua.utils.TEST_RESULT
import com.deadely.it_lingua.utils.subscribeAndObserve
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class TestsPresenter @Inject constructor(private val repository: Repository) :
    BasePresenter<TestsView>() {
    override fun onFirstViewAttach() {
        router.setResultListener(TEST_RESULT) {
            if ((it as Int) == 1) {
                updateUserData()
            }
        }
        viewState.initView()
        getApiTests()
    }

    private fun updateUserData() {
        val userId = repository.getActiveUser()?.id ?: ""
        repository.getUserById(userId).subscribeAndObserve().subscribe(
            { user ->
                repository.saveActiveUser(user)
                getApiTests()
            },
            { error -> ErrorUtils.proceed(error) { viewState.showError(it) } }
        )
    }

    private fun getApiTests() {
        viewState.showProgress(true)
        repository.getTests().subscribeAndObserve()
            .subscribe(
                { data ->
                    setCheckMarkList(data)
                },
                { e ->
                    ErrorUtils.proceed(e) {
                        viewState.showProgress(false)
                        viewState.showError(it)
                    }
                }
            )
    }

    private fun setCheckMarkList(list: List<Test>) {
        val sortedList = list.sortedBy { it.title }
        val count = repository.getActiveUser()?.stats?.last()?.countLessons ?: 0
        for (i in 0 until count) {
            sortedList[i].isChecked = true
        }
        viewState.showProgress(false)
        viewState.setTestsList(sortedList)
    }

    fun exit() {
        router.replaceScreen(Screens.HOME_SCREEN())
    }

    fun openDetailTest(test: Test) {
        router.navigateTo(TEST_DETAIL_SCREEN(test))
    }
}
