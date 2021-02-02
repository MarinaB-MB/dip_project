package com.deadely.it_lingua.ui.reg

import android.content.Context
import com.deadely.it_lingua.R
import com.deadely.it_lingua.base.BasePresenter
import com.deadely.it_lingua.model.User
import com.deadely.it_lingua.model.UserBody
import com.deadely.it_lingua.navigation.Screens
import com.deadely.it_lingua.repository.Repository
import com.deadely.it_lingua.utils.ErrorUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class RegistrationPresenter @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: Repository
) : BasePresenter<RegistrationView>() {

    private val disposable = CompositeDisposable()
    private var isRegMode = false

    fun exit() {
        router.exit()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        repository.getActiveUser()?.let {
            isRegMode = false
        } ?: run {
            isRegMode = true
        }
        viewState.initView(isRegMode)
    }

    private fun openMainScreen() {
        router.newRootScreen(Screens.MAIN_SCREEN())
    }

    fun validateFields(vararg disposables: Disposable) {
        with(disposable) {
            clear()
            for (dis in disposables) {
                add(dis)
            }
        }
    }

    fun isEmailValid(string: String) {
        if (string.isEmpty()) {
            viewState.setEmailError(context.getString(R.string.empty_value_error))
        } else if (string.contains("@") && string.contains(".")) {
            viewState.setEmailError(null)
        } else {
            viewState.setEmailError(context.getString(R.string.incorrect_email_error))
        }
    }

    fun isPasswordValid(string: String) {
        when {
            string.length > 16 -> {
                viewState.setPasswordError(context.getString(R.string.incorrect_password_max_length_error))
            }
            string.length < 5 -> {
                viewState.setPasswordError(context.getString(R.string.incorrect_password_min_length_error))
            }
            else -> {
                viewState.setPasswordError(null)
            }
        }
    }

    fun isNameValid(name: String) {
        when {
            name.length > 16 -> {
                viewState.setNameError(context.getString(R.string.incorrect_name_length_error))
            }
            name.isEmpty() -> {
                viewState.setNameError(context.getString(R.string.empty_value_error))
            }
            else -> {
                viewState.setNameError(null)
            }
        }
    }

    fun checkUser(email: String, password: String, name: String) {
        if (isRegMode) {
            createUser(email, password, name)
        } else {
            compareData(email, password)
        }
    }

    private fun createUser(email: String, password: String, name: String) {
        repository.createUser(
            UserBody(
                email = email,
                name = name,
                password = password,
                active = true,
                stats = listOf()
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { data ->
                    saveActiveUser(data)
                },
                { e ->
                    ErrorUtils.proceed(e) { viewState.showError(it) }
                }
            )
    }

    private fun compareData(email: String, password: String) {
        repository.getUserByEmail(email).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                { data ->
                    val user = data.firstOrNull()
                    if (email == user?.email) {
                        if (password == user.password) {
                            saveActiveUser(user)
                            openMainScreen()
                        } else {
                            viewState.showError(context.getString(R.string.password_compare_error))
                        }
                    } else {
                        viewState.showError(context.getString(R.string.email_compare_error))
                    }
                },
                { e ->
                    ErrorUtils.proceed(e) { viewState.showError(it) }
                }
            )
    }

    private fun saveActiveUser(data: User?) {
        data?.let {
            repository.saveActiveUser(it)
            openMainScreen()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

    fun changeMode() {
        isRegMode = !isRegMode
        viewState.initView(isRegMode)
    }
}
