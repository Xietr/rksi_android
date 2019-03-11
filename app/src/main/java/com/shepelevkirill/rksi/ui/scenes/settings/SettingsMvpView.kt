package com.shepelevkirill.rksi.ui.scenes.settings

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface SettingsMvpView : MvpView {

    fun showToast(message: String)

    fun setSelection(group: String)

    fun showGroups(groups: List<String>)
    fun clearGroups()

}