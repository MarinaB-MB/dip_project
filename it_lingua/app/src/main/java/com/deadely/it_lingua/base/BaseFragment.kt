package com.deadely.it_lingua.base

import android.os.Bundle
import android.view.View
import moxy.MvpAppCompatFragment

abstract class BaseFragment(layout: Int) :
    MvpAppCompatFragment(layout),
    BaseActivity.BackButtonPressed,
    BaseActivity.ExitListener {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getExtras()
        setListeners()
        initView()
    }

    abstract fun initView()
    abstract fun getExtras()
    abstract fun setListeners()
}
