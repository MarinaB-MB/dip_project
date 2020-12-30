package com.korkel.mc.ui.main

import com.korkel.mc.base.BaseView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface MainView : BaseView {
    @AddToEndSingle
    fun aaa()
}
