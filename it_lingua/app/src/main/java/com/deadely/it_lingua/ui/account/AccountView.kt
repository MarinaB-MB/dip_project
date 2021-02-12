package com.deadely.it_lingua.ui.account

import com.deadely.it_lingua.base.BaseView
import com.deadely.it_lingua.model.User
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(SkipStrategy::class)
interface AccountView : BaseView {
    fun initView(user: User?)
    fun showEditDialog(activeUser: User?)
    fun showError(error: String)
    fun showProgress(isShow: Boolean)
}
