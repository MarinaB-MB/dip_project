package com.deadely.it_lingua.ui.main

import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.deadely.it_lingua.R
import com.deadely.it_lingua.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class MainActivity : BaseActivity(R.layout.activity_main), BaseActivity.ExitListener, BaseActivity.BackButtonPressed {

    private lateinit var navController: NavController
    @Inject
    lateinit var provider: Provider<MainPresenter>
    private val presenter by moxyPresenter { provider.get() }

    override fun initView() {
        navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_lessons,
                R.id.navigation_tests,
                R.id.navigation_dictionary
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun getExtras() {
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
}
