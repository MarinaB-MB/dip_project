package com.deadely.it_lingua.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.deadely.it_lingua.R
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.material.bottomnavigation.BottomNavigationView
import moxy.MvpAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity(layout: Int) : MvpAppCompatActivity(layout), BaseView {
    @Inject
    lateinit var router: Router
    var btmNavView: BottomNavigationView? = null

    @Inject
    lateinit var navigationHolder: NavigatorHolder

    private val navigator: Navigator = AppNavigator(this, R.id.main_container)
    override fun onResume() {
        super.onResume()
        navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigationHolder.removeNavigator()
        super.onPause()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getExtras()
        setListeners()
        initView()
    }

    override fun onBackPressed() {
        val fm = supportFragmentManager
        var fragment: Fragment? = null
        val fragments = fm.fragments
        for (f in fragments) {
            if (f.isVisible) {
                fragment = f
                break
            }
        }
        val childFm = fragment?.childFragmentManager
        var childFragment: Fragment? = null
        childFm?.let {
            for (f in childFm.fragments) {
                if (f.isVisible) {
                    childFragment = f
                    break
                }
            }
        }
        if (childFragment != null && (childFragment is BackButtonPressed)) {
            childFm?.let {
                if (it.backStackEntryCount > 0) {
                    (childFragment as BackButtonPressed).onBackButtonPressed()
                    btmNavView?.menu?.getItem(0)?.isChecked = true
                    return
                } else {
                    (childFragment as ExitListener).exitPressed()
                    btmNavView?.menu?.getItem(0)?.isChecked = true
                    return
                }
            }
        } else {
            if (fragment != null && fragment is BackButtonPressed) {
                (fragment as BackButtonPressed).onBackButtonPressed()
//                btmNavView?.menu?.getItem(0)?.isChecked = true
                return
            } else {
                router.exit()
                return
            }
        }
    }

    abstract fun initView()
    abstract fun getExtras()
    abstract fun setListeners()

    interface BackButtonPressed {
        fun onBackButtonPressed()
    }

    interface ExitListener {
        fun exitPressed()
    }
}
