package com.shepelevkirill.rksi.ui.scenes.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.shepelevkirill.rksi.ui.scenes.schedule.ScheduleFragment
import com.shepelevkirill.rksi.ui.scenes.settings.SettingsFragment

@InjectViewState
class MainPresenter : MvpPresenter<MainMvpView>() {
    private val scheduleFragment = ScheduleFragment()
    private val settingsFragment = SettingsFragment()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.openFragment(scheduleFragment)
    }

    fun onSchedulePressed() {
        viewState.openFragment(scheduleFragment)
    }

    fun onSettingsPressed() {
        viewState.openFragment(settingsFragment)
    }

}