package com.korkel.mc.ui.frag

import com.korkel.mc.R
import com.korkel.mc.base.BaseActivity
import com.korkel.mc.base.BaseFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class AFragment : BaseFragment(R.layout.a_fragment), BaseActivity.BackButtonPressed {
    @Inject
    lateinit var presenterProvider: Provider<AFragmentPresenter>

    private val presenter by moxyPresenter { presenterProvider.get() }

    override fun getExtras() {
    }

    override fun initView() {
    }

    override fun onBackButtonPressed() {
        presenter
    }
}
