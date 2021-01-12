package com.deadely.it_lingua.ui.main

import com.deadely.it_lingua.base.BaseView
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(SkipStrategy::class)
interface MainView : BaseView
