package com.deadely.it_lingua.ui.home

import com.deadely.it_lingua.base.BaseView
import com.deadely.it_lingua.model.User
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndStrategy::class)
interface HomeView : BaseView {
    fun setUserData(activeUser: User?, graphMode: String = HomeFragment.LESSON_MODE)
    fun initView()
    fun showError(error: String)
    fun showProgress(isShow: Boolean)
}
