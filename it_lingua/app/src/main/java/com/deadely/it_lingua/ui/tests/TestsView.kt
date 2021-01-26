package com.deadely.it_lingua.ui.tests

import com.deadely.it_lingua.base.BaseView
import com.deadely.it_lingua.model.Test
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(SkipStrategy::class)
interface TestsView : BaseView {
    fun setTestsList(data: List<Test>)
    fun showProgress(isShow: Boolean)
    fun showError(error: String)
}
