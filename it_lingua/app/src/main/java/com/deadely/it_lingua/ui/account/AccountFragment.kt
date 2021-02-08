package com.deadely.it_lingua.ui.account

import by.kirich1409.viewbindingdelegate.viewBinding
import com.deadely.it_lingua.R
import com.deadely.it_lingua.base.BaseFragment
import com.deadely.it_lingua.databinding.ActivityAccountBinding
import com.deadely.it_lingua.model.User
import com.deadely.it_lingua.utils.showBottomNavView
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
        }
    }

    override fun initView(user: User?) {
    }

    override fun onBackButtonPressed() {
        presenter.exit()
    }

    override fun exitPressed() {
        presenter.exit()
    }
}
