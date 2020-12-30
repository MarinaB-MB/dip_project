package com.korkel.mc.ui.main

import android.widget.Toast
import com.korkel.mc.R
import com.korkel.mc.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class MainActivity : BaseActivity(R.layout.activity_main), MainView {

    @Inject
    lateinit var presenterProvider: Provider<MainPresenter>

    private val presenter by moxyPresenter { presenterProvider.get() }

    override fun getExtras() {
        presenter.openFragment()
    }

    override fun aaa() {
        Toast.makeText(this, "AAAAA", Toast.LENGTH_SHORT).show()
    }
}
