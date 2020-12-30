package com.deadely.it_lingua.base

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import moxy.MvpView
import javax.inject.Inject

abstract class BasePresenter<T : MvpView> : MvpPresenter<T>() {

    @Inject
    lateinit var router: Router
}
