package com.korkel.mc.ui.main

import android.content.Context
import com.korkel.mc.base.BasePresenter
import com.korkel.mc.navigation.Screens
import dagger.hilt.android.qualifiers.ApplicationContext
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(@ApplicationContext val context: Context) :
    BasePresenter<MainView>() {

    fun openFragment() {
        router.navigateTo(Screens.FragmentExample())
//        Toast.makeText(context, context.getString(R.string.app_name), Toast.LENGTH_SHORT).show()
//        viewState.aaa()
    }
}
