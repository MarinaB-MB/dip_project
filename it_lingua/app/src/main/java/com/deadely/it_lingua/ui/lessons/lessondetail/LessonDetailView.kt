package com.deadely.it_lingua.ui.lessons.lessondetail

import com.deadely.it_lingua.base.BaseView
import com.deadely.it_lingua.model.Lesson
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(SkipStrategy::class)
interface LessonDetailView : BaseView {
    fun showProgress(isShow: Boolean)
    fun initView(lesson: Lesson?)
    fun showError(error: String)
}
