package com.deadely.it_lingua.ui.lessons

import com.deadely.it_lingua.R
import com.deadely.it_lingua.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class LessonsFragment : BaseFragment(R.layout.fragment_lessons), LessonsView {
    @Inject
    lateinit var provider: Provider<LessonsPresenter>
    private val presenter by moxyPresenter { provider.get() }
    override fun initView() {
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
