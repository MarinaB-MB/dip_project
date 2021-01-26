package com.deadely.it_lingua.ui.reg

import android.widget.ScrollView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.deadely.it_lingua.R
import com.deadely.it_lingua.base.BaseActivity
import com.deadely.it_lingua.databinding.ActivityRegistrationBinding
import com.deadely.it_lingua.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_registration.*
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class RegistrationActivity :
    BaseActivity(R.layout.activity_registration),
    BaseActivity.ExitListener,
    BaseActivity.BackButtonPressed,
    RegistrationView {
    @Inject
    lateinit var provider: Provider<RegistrationPresenter>
    private val presenter by moxyPresenter { provider.get() }
    private val viewBinding by viewBinding(
        ActivityRegistrationBinding::bind,
        R.id.nsvRegistrationContainer
    )

    var isRegMode = false
        set(value) {
            field = value
            initView()
        }

    override fun initView() {
        supportActionBar?.hide()
        if (isRegMode) {
            tilName.makeVisible()
        } else {
            tilName.makeGone()
        }
    }

    override fun getExtras() {
    }

    override fun setListeners() {
        with(viewBinding) {
            btnAccount.setOnClickListener {
                presenter.changeMode()
                nsvRegistrationContainer.fullScroll(ScrollView.FOCUS_UP)
            }
            btnNextStep.setOnClickListener {
                this@RegistrationActivity.hideKeyboard()
                if (checkFields()) presenter.checkUser(
                    tietEmail.text.toString().trim(),
                    tietPassword.text.toString().trim(),
                    tietName.text.toString().trim()
                )
            }
        }
        validateFields()
    }

    private fun checkFields(): Boolean {
        with(viewBinding) {
            return if (isRegMode) {
                tilEmail.error == null && tilPassword.error == null && tilName.error == null
            } else {
                tilEmail.error == null && tilPassword.error == null
            }
        }
    }

    private fun validateFields() {
        with(viewBinding) {
            presenter.validateFields(
                validateInput(
                    tilEmail,
                    tietEmail
                ) { presenter.isEmailValid(tietEmail.text.toString()) },
                validateInput(
                    tilPassword,
                    tietPassword
                ) { presenter.isPasswordValid(tietPassword.text.toString()) },
                validateInput(
                    tilName,
                    tietName
                ) { presenter.isNameValid(tietName.text.toString()) }
            )
        }
    }

    override fun showRegistrationMode(isRegistrationMode: Boolean) {
        isRegMode = isRegistrationMode
    }

    override fun setNameError(error: String?) {
        viewBinding.tilName.error = error
    }

    override fun setEmailError(error: String?) {
        viewBinding.tilEmail.error = error
    }

    override fun setPasswordError(error: String?) {
        viewBinding.tilPassword.error = error
    }

    override fun showError(error: String) {
        viewBinding.btnNextStep.snack(error)
    }

    override fun onBackButtonPressed() {
        presenter.exit()
    }

    override fun exitPressed() {
        presenter.exit()
    }
}
