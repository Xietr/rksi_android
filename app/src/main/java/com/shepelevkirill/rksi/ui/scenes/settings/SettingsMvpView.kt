package com.shepelevkirill.rksi.ui.scenes.settings

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface SettingsMvpView : MvpView {

    fun showToast(message: String)

    fun setSelection(group: String)

    fun showGroups(groups: List<String>)
    fun clearGroups()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setIsGroupProgressBarVisible(isVisible: Boolean)
}