package com.deadely.it_lingua.ui.testdetail

import com.deadely.it_lingua.base.BaseView
import com.deadely.it_lingua.model.Ask
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(SkipStrategy::class)
interface TestDetailView : BaseView {
    fun initView(ask: Ask?, answers: List<String>)
    fun showResultDialog(badCount: String, goodCount: String)
    fun showError(error: String)
    fun closeScreen()
    fun showEndConfirmDialog()
    fun blockButtons()
    fun showProgress(isShow: Boolean)
}
