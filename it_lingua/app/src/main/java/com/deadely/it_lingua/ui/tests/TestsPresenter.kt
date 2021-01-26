package com.deadely.it_lingua.ui.tests

import com.deadely.it_lingua.base.BasePresenter
import com.deadely.it_lingua.model.Test
import com.deadely.it_lingua.navigation.Screens
import com.deadely.it_lingua.repository.Repository
import com.deadely.it_lingua.utils.ErrorUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class TestsPresenter @Inject constructor(private val repository: Repository) :
    BasePresenter<TestsView>() {
    override fun onFirstViewAttach() {
        getApiTests()
    }

    private fun getApiTests() {
        repository.getTests().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { data ->
                    val sortedList = data.sortedBy { it.title }
                    setCheckMarkList(sortedList)
                },
                { e -> ErrorUtils.proceed(e) { viewState.showError(it) } }
            )
    }

    private fun setCheckMarkList(sortedList: List<Test>) {
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
        // OPEN ACTIVITY
    }
}
