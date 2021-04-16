package com.deadely.it_lingua.ui.account

import android.app.Dialog
import android.widget.Button
import android.widget.ImageView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.deadely.it_lingua.R
import com.deadely.it_lingua.base.BaseFragment
import com.deadely.it_lingua.databinding.ActivityAccountBinding
import com.deadely.it_lingua.model.User
import com.deadely.it_lingua.utils.makeGone
import com.deadely.it_lingua.utils.makeVisible
import com.deadely.it_lingua.utils.showBottomNavView
import com.deadely.it_lingua.utils.snack
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class AccountFragment : BaseFragment(R.layout.activity_account), AccountView {
    @Inject
    lateinit var provider: Provider<AccountPresenter>
    private val presenter by moxyPresenter { provider.get() }
    private val viewBinding by viewBinding(
        ActivityAccountBinding::bind
    )

    companion object {
        fun newInstance() = AccountFragment()
    }

    override fun setListeners() {
        showBottomNavView(false)
        with(viewBinding) {
            btnExit.setOnClickListener {
                presenter.logout()
            }
            tvDeleteAcc.setOnClickListener { showConfirmDialog() }
            ivDeleteAcc.setOnClickListener { showConfirmDialog() }
            tvEditAcc.setOnClickListener { presenter.openEditDialog() }
            ivEditAcc.setOnClickListener { presenter.openEditDialog() }
        }
    }

    private fun showConfirmDialog() {
        context?.let {
            Dialog(it).apply {
                setContentView(R.layout.dialog_confirm_close_test)
                window?.setBackgroundDrawableResource(R.drawable.bg_dialog)
                findViewById<Button>(R.id.btnNo).setOnClickListener {
                    dismiss()
                }
                findViewById<Button>(R.id.btnYes).setOnClickListener {
                    presenter.deleteAccount()
                    dismiss()
                }
            }.show()
        }
    }

    override fun initView(user: User?) {}

    override fun showEditDialog(activeUser: User?) {
        context?.let {
            Dialog(it).apply {
                setContentView(R.layout.dialog_edit_user)
                window?.setBackgroundDrawableResource(R.drawable.bg_dialog)

                val tilPassword = findViewById<TextInputLayout>(R.id.tilPassword)
                val etPassword = findViewById<TextInputEditText>(R.id.tietPassword)

                etPassword.setText(activeUser?.password)
                etPassword.setSelection(etPassword.text?.length ?: 0)
                findViewById<ImageView>(R.id.ivClose).setOnClickListener { dismiss() }
                findViewById<Button>(R.id.btnConfirmEdit).setOnClickListener {
                    val newPassword = etPassword.text?.trim().toString()
                    val error = presenter.isPasswordValid(newPassword = newPassword)
                    if (error == -1) {
                        presenter.updatePassword(newPassword)
                        dismiss()
                    } else {
                        tilPassword.error = getString(error)
                    }
                }
                setCanceledOnTouchOutside(false)
            }.show()
        }
    }

    override fun showError(error: String) {
        viewBinding.rlAccountContainer.snack(error)
    }

    override fun showProgress(isShow: Boolean) {
        if (isShow) {
            viewBinding.apply {
                pvLoad.makeVisible()
                btnExit.makeGone()
                tvDeleteAcc.makeGone()
                ivDeleteAcc.makeGone()
                tvEditAcc.makeGone()
                ivEditAcc.makeGone()
            }
        } else {
            viewBinding.apply {
                pvLoad.makeGone()
                btnExit.makeVisible()
                tvDeleteAcc.makeVisible()
                ivDeleteAcc.makeVisible()
                tvEditAcc.makeVisible()
                ivEditAcc.makeVisible()
            }
        }
    }

    override fun onBackButtonPressed() {
        presenter.exit()
    }

    override fun exitPressed() {
        presenter.exit()
    }
}
