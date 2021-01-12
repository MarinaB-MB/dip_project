package com.deadely.it_lingua.ui.tests

import com.deadely.it_lingua.R
import com.deadely.it_lingua.base.BaseFragment
import com.deadely.it_lingua.utils.setActivityTitle
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class TestsFragment : BaseFragment(R.layout.fragment_tests), TestsView {
    @Inject
    lateinit var provider: Provider<TestsPresenter>
    private val presenter by moxyPresenter { provider.get() }

    override fun initView() {
        setActivityTitle(R.string.title_tests)
    }

    override fun setListeners() {
    }

    override fun getExtras() {
    }

    override fun onBackButtonPressed() {
        presenter.exit()
    }

    override fun exitPressed() {
        presenter.exit()
    }
}
