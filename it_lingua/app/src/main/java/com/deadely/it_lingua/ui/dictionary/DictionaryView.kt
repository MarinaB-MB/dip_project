package com.deadely.it_lingua.ui.dictionary

import com.deadely.it_lingua.base.BaseView
import com.deadely.it_lingua.model.Word
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(SkipStrategy::class)
interface DictionaryView : BaseView {
    fun setWordList(list: List<Word>)
    fun clearList()
    fun showError(error: String)
    fun showProgress(isShow: Boolean)
}
