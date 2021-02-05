package com.deadely.it_lingua.ui.lessons

import com.deadely.it_lingua.base.BaseView
import com.deadely.it_lingua.model.Lesson
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(SkipStrategy::class)
interface LessonsView : BaseView {
    fun showError(error: String)
    fun setLessonsList(data: List<Lesson>)
    fun showProgress(isShow: Boolean)
    fun initView()
}
