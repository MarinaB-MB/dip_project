package com.korkel.mc.base

import android.content.Context
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.qualifiers.ApplicationContext
import moxy.MvpPresenter
import moxy.MvpView
import javax.inject.Inject

abstract class BasePresenter<T : MvpView> : MvpPresenter<T>() {

    @Inject
    lateinit var router: Router
//
//    @ApplicationContext
//    lateinit var context: Context
}
