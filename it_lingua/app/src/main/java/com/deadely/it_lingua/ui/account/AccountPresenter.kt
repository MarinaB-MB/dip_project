package com.deadely.it_lingua.ui.account

import com.deadely.it_lingua.base.BasePresenter
import com.deadely.it_lingua.repository.Repository
import com.deadely.it_lingua.utils.ACCOUNT_RESULT
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class AccountPresenter @Inject constructor(private val repository: Repository) :
    BasePresenter<AccountView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getActiveUser()
    }

    private fun getActiveUser() {
        val user = repository.getActiveUser()
        viewState.initView(user)
    }

    fun logout() {
        router.sendResult(ACCOUNT_RESULT, 1)
    }
}
