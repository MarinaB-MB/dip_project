package com.deadely.it_lingua.ui.home

import com.deadely.it_lingua.base.BasePresenter
import com.deadely.it_lingua.repository.Repository
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class HomePresenter @Inject constructor(private val repository: Repository) :
    BasePresenter<HomeView>() {
    override fun onFirstViewAttach() {
        viewState.showConnect()
    }
    fun exit() {
        router.exit()
    }
}
