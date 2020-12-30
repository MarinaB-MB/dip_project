package com.deadely.it_lingua.ui.main

import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.deadely.it_lingua.R
import com.deadely.it_lingua.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(R.layout.activity_main) {

    private lateinit var navController: NavController

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

            }
            true
        }
    }
}
