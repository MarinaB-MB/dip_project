package com.korkel.mc.ui.frag

import com.korkel.mc.base.BasePresenter
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class AFragmentPresenter @Inject constructor() : BasePresenter<AFragmentView>() {
    fun exit() {
        router.exit()
    }
}
