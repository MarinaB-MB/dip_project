package com.korkel.mc.base

import android.os.Bundle
import android.view.View
import moxy.MvpAppCompatFragment

abstract class BaseFragment(layoutId: Int) : MvpAppCompatFragment(layoutId) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getExtras()
        initView()
    }

    abstract fun getExtras()
    abstract fun initView()
}