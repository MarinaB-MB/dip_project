package com.deadely.it_lingua.ui.lessons

import com.deadely.it_lingua.base.BasePresenter
import com.deadely.it_lingua.model.Lesson
import com.deadely.it_lingua.navigation.Screens
import com.deadely.it_lingua.repository.Repository
import com.deadely.it_lingua.utils.ErrorUtils
import com.deadely.it_lingua.utils.LESSON_RESULT
import com.deadely.it_lingua.utils.subscribeAndObserve
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class LessonsPresenter @Inject constructor(private val repository: Repository) :
    BasePresenter<LessonsView>() {

    override fun onFirstViewAttach() {
        setResultListeners()
        viewState.initView()
        getLessons()
    }

    private fun setResultListeners() {
        router.setResultListener(LESSON_RESULT) {
            if ((it as Int) == 1) {
                updateUserData()
            }
        }
    }

    private fun updateUserData() {
        val userId = repository.getActiveUser()?.id ?: ""
        repository.getUserById(userId).subscribeAndObserve().subscribe(
            { user ->
                repository.saveActiveUser(user)
                getLessons()
                setResultListeners()
            },
            { error -> ErrorUtils.proceed(error) { viewState.showError(it) } }
        )
    }

    private fun getLessons() {
        viewState.showProgress(true)
        repository.getLessons().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                { data ->
                    val sortedList = data.sortedBy { it.title }
                    setCheckMarkList(sortedList)
                },
                { e -> ErrorUtils.proceed(e) { viewState.showError(it) } }
            )
    }

    private fun setCheckMarkList(data: List<Lesson>) {
        val count = repository.getActiveUser()?.stats?.last()?.countLessons ?: 0
        for (i in 0 until count) {
            data[i].isChecked = true
        }
        viewState.showProgress(false)
        viewState.setLessonsList(data)
    }

    fun exit() {
        router.replaceScreen(Screens.HOME_SCREEN())
    }

    fun openDetailLesson(lesson: Lesson) {
        router.navigateTo(Screens.LESSON_DETAIL_SCREEN(lesson))
    }
}
