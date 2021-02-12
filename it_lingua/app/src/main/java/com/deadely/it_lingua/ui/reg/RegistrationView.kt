package com.deadely.it_lingua.ui.reg

import com.deadely.it_lingua.base.BaseView
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(SkipStrategy::class)
interface RegistrationView : BaseView {
    fun setEmailError(error: String?)
    fun setPasswordError(error: String?)
    fun setNameError(error: String?)
    fun showError(error: String)
    fun initView(regMode: Boolean)
}
