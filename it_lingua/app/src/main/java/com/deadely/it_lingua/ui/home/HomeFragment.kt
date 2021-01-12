package com.deadely.it_lingua.ui.home

import android.util.Log
import com.deadely.it_lingua.R
import com.deadely.it_lingua.base.BaseFragment
import com.deadely.it_lingua.utils.setActivityTitle
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home), HomeView {
    @Inject
    lateinit var provider: Provider<HomePresenter>
    private val presenter by moxyPresenter { provider.get() }
    override fun initView() {
        setActivityTitle(R.string.title_home)
    }

    override fun showConnect() {
        Log.e("TAG", "showConnect: ")
    }

    override fun getExtras() {
    }

    override fun setListeners() {
    }

    override fun onBackButtonPressed() {
        presenter.exit()
    }

    override fun exitPressed() {
        presenter.exit()
    }
}
