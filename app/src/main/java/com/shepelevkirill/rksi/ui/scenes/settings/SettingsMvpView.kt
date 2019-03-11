package com.shepelevkirill.rksi.ui.scenes.settings

import com.arellomobile.mvp.MvpView

interface SettingsMvpView : MvpView {

    fun showToast(message: String)

    fun setSelection(group: String)

    fun showGroups(groups: List<String>)
    fun clearGroups()

}