package com.deadely.it_lingua.ui.home

import com.deadely.it_lingua.base.BaseView
import com.deadely.it_lingua.model.User
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(SkipStrategy::class)
interface HomeView : BaseView {
    fun setUserData(activeUser: User?)
}
