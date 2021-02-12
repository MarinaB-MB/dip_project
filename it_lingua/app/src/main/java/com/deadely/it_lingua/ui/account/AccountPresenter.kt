package com.deadely.it_lingua.ui.account

import com.deadely.it_lingua.R
import com.deadely.it_lingua.base.BasePresenter
import com.deadely.it_lingua.model.User
import com.deadely.it_lingua.repository.Repository
import com.deadely.it_lingua.utils.ACCOUNT_RESULT
import com.deadely.it_lingua.utils.DELETE_ACCOUNT_RESULT
import com.deadely.it_lingua.utils.ErrorUtils
import com.deadely.it_lingua.utils.subscribeAndObserve
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class AccountPresenter @Inject constructor(private val repository: Repository) :
    BasePresenter<AccountView>() {
    private var activeUser: User? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getActiveUser()
    }

    private fun getActiveUser() {
        activeUser = repository.getActiveUser()
        viewState.initView(activeUser)
    }

    fun logout() {
        router.sendResult(ACCOUNT_RESULT, 1)
    }

    fun exit() {
        router.exit()
    }

    fun isPasswordValid(newPassword: String): Int {
        return when {
            newPassword.length > 16 -> {
                R.string.incorrect_password_max_length_error
            }
            newPassword.length < 5 -> {
                R.string.incorrect_password_min_length_error
            }
            else -> {
                -1
            }
        }
    }

    fun openEditDialog() {
        viewState.showEditDialog(activeUser)
    }

    fun updatePassword(newPassword: String) {
        viewState.showProgress(true)
        activeUser?.let {
            it.password = newPassword
            repository.updateUser(it).subscribeAndObserve().doFinally {
                viewState.showProgress(false)
            }
                .subscribe(
                    { user ->
                        repository.saveActiveUser(user)
                        activeUser = user
                        viewState.initView(user)
                    },
                    { error ->
                        ErrorUtils.proceed(error) { t -> viewState.showError(t) }
                    }
                )
        }
    }

    fun deleteAccount() {
        repository.deleteUser(activeUser?.id ?: "").subscribeAndObserve().subscribe(
            {
                router.sendResult(DELETE_ACCOUNT_RESULT, 1)
            },
            { error ->
                ErrorUtils.proceed(error) { t -> viewState.showError(t) }
            }
        )
    }
}
