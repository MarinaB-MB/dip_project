package com.deadely.it_lingua.ui.lessons

import com.deadely.it_lingua.base.BaseView
import com.deadely.it_lingua.model.Lesson
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndStrategy::class)
interface LessonsView : BaseView {
    fun showError(error: String)
    fun showProgress(isShow: Boolean)
    fun initView(data: List<Lesson>)
    fun showPreviewDialog()
}
