package com.deadely.it_lingua.ui.lessons.lessondetail

import com.deadely.it_lingua.base.BasePresenter
import com.deadely.it_lingua.repository.Repository
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class LessonDetailPresenter @Inject constructor(private val repository: Repository) :
    BasePresenter<LessonDetailView>() {
    fun exit() {
        router.exit()
    }
}