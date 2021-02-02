package com.deadely.it_lingua.ui.testdetail

import com.deadely.it_lingua.base.BasePresenter
import com.deadely.it_lingua.model.Ask
import com.deadely.it_lingua.model.StatCreateBody
import com.deadely.it_lingua.model.Test
import com.deadely.it_lingua.model.User
import com.deadely.it_lingua.repository.Repository
import com.deadely.it_lingua.utils.ErrorUtils
import com.deadely.it_lingua.utils.PATTERN_3
import com.deadely.it_lingua.utils.formatWithPattern
import com.deadely.it_lingua.utils.subscribeAndObserve
import moxy.InjectViewState
import java.util.*
import javax.inject.Inject

@InjectViewState
class TestDetailPresenter @Inject constructor(private val repository: Repository) :
    BasePresenter<TestDetailView>() {
    private var rightAnswer = ""
    private var answers = mutableListOf<String>()
    private var askIndex = 0
    private var rightAnswersCount = 0
    private var ask: Ask? = null
    var test: Test? = null
    private var isEnd = false
    fun exit() {
        if (isEnd) {
            router.exit()
        } else {
            viewState.showEndConfirmDialog()
        }
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getCurrentAsk()
    }

    private fun getCurrentAsk() {
        ask = test?.asks?.get(askIndex)
        ask?.let {
            answers = it.answers.split(",").toMutableList()
            setRightAnswer()
            viewState.initView(ask, answers)
        }
    }

    private fun setRightAnswer() {
        for (answr in answers) {
            if (answr.contains("1")) {
                val indexOfElement = answers.indexOf(answr)
                answers.remove(answr)
                val updatesAnswer = answr.replace("1", "")
                answers.add(indexOfElement, updatesAnswer)
                rightAnswer = updatesAnswer
                break
            }
        }
    }

    fun checkAnswer(index: Int) {
        if (rightAnswer == answers[index]) {
            rightAnswersCount++
        }
        updateAsk()
    }

    private fun updateAsk() {
        if (askIndex == test?.asks?.size?.minus(1) ?: 0) {
            finishTest()
        } else {
            askIndex++
            getCurrentAsk()
        }
    }

    private fun finishTest() {
        val activeUser = repository.getActiveUser()
        activeUser?.let { user ->
            val countTest = user.stats?.last()?.countTests?.plus(1) ?: 0
            val countLessons = user.stats?.last()?.countLessons ?: 0
            val stat = StatCreateBody(
                formatWithPattern(Calendar.getInstance().time, PATTERN_3),
                countTest,
                countLessons
            )
            val updatedStats = user.stats?.toMutableList() ?: mutableListOf()
            repository.createStat(stat).subscribeAndObserve()
                .subscribe(
                    { stat ->
                        updatedStats.add(stat)
                        user.stats = updatedStats
                        updateUsersStats(user)
                    },
                    { error -> ErrorUtils.proceed(error) { viewState.showError(it) } }
                )
        }
    }

    private fun updateUsersStats(user: User) {
        repository.updateUser(user.id, user).subscribeAndObserve()
            .subscribe(
                {
                    showResultDialog()
                },
                { e ->
                    ErrorUtils.proceed(e) {
                        viewState.showError(it)
                        viewState.closeScreen()
                    }
                }
            )
    }

    private fun showResultDialog() {
        isEnd = true
        val badCount = test?.asks?.size?.minus(rightAnswersCount)
        viewState.showResultDialog(
            badCount.toString(),
            rightAnswersCount.toString()
        )
    }
}
