package com.deadely.it_lingua.ui.main

import by.kirich1409.viewbindingdelegate.viewBinding
import com.deadely.it_lingua.R
import com.deadely.it_lingua.base.BaseActivity
import com.deadely.it_lingua.databinding.ActivityMainBinding
import com.deadely.it_lingua.utils.makeGone
import com.deadely.it_lingua.utils.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class MainActivity :
    BaseActivity(R.layout.activity_main),
    BaseActivity.ExitListener,
    BaseActivity.BackButtonPressed,
    MainView {

    @Inject
    lateinit var provider: Provider<MainPresenter>
    private val presenter by moxyPresenter { provider.get() }
    private val viewBinding by viewBinding(ActivityMainBinding::bind)

    override fun initView() {
        btmNavView = navView
        presenter.openHomeScreen()
    }

    override fun setListeners() {
        navView.setOnNavigationItemSelectedListener { item ->
            if (!item.isChecked) {
                when (item.itemId) {
                    R.id.navigation_home -> presenter.openHomeScreen()
                    R.id.navigation_lessons -> presenter.openLessonsScreen()
                    R.id.navigation_tests -> presenter.openTestsScreen()
                    R.id.navigation_dictionary -> presenter.openDictionaryScreen()
                }
            }
            true
        }
    }

    override fun onBackButtonPressed() {
        presenter.exit()
    }

    override fun exitPressed() {
        presenter.exit()
    }

    fun showBottomNavView(isShow: Boolean) {
        if (isShow) {
            viewBinding.navView.makeVisible()
        } else {
            viewBinding.navView.makeGone()
        }
    }
}
