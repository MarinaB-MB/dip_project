package com.deadely.it_lingua.ui.lessons.lessondetail

import com.deadely.it_lingua.base.BasePresenter
import com.deadely.it_lingua.model.Lesson
import com.deadely.it_lingua.model.StatCreateBody
import com.deadely.it_lingua.model.User
import com.deadely.it_lingua.repository.Repository
import com.deadely.it_lingua.utils.ErrorUtils
import com.deadely.it_lingua.utils.LESSON_RESULT
import com.deadely.it_lingua.utils.getCurrentDateWithoutTime
import com.deadely.it_lingua.utils.subscribeAndObserve
import moxy.InjectViewState
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class LessonDetailPresenter @Inject constructor(private val repository: Repository) :
    BasePresenter<LessonDetailView>() {

    private val date = getCurrentDateWithoutTime()

    var lesson: Lesson? = null
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.initView(lesson)
        if (lesson?.isChecked == false) {
            updateStats()
        }
    }

    private fun updateStats() {
        val activeUser = repository.getActiveUser()
        activeUser?.let { user ->
            val stat = createStatBody(user)

            repository.getStatByDate(date)?.let {
                updateStat(it.id, stat)
            } ?: run {
                createNewStat(user, stat)
            }
        }
    }

    private fun updateStat(id: String, stat: StatCreateBody) {
        repository.updateStat(id, stat).subscribeAndObserve().subscribe(
            { stata -> Timber.d("updateStat") },
            { error ->
                ErrorUtils.proceed(error) { viewState.showError(it) }
            }
        )
    }

    private fun createNewStat(user: User, stat: StatCreateBody) {
        repository.createStat(stat).subscribeAndObserve()
            .subscribe(
                { stat ->
                    val updatedStats = user.stats?.toMutableList() ?: mutableListOf()
                    updatedStats.add(stat)
                    user.stats = updatedStats
                    updateUsersStats(user)
                },
                { error -> ErrorUtils.proceed(error) { viewState.showError(it) } }
            )
    }

    private fun createStatBody(user: User): StatCreateBody {
        val countTest = user.stats?.last()?.countTests ?: 0
        val countLessons = user.stats?.last()?.countLessons?.plus(1) ?: 0
        return StatCreateBody(
            date,
            countTest,
            countLessons
        )
    }

    private fun updateUsersStats(user: User) {
        repository.updateUser(user.id, user).subscribeAndObserve().subscribe()
    }

    fun exit() {
        router.sendResult(LESSON_RESULT, if (lesson?.isChecked == true) 0 else 1)
        router.exit()
    }
}
