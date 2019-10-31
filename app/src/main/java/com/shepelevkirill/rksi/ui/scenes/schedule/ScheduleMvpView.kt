package com.shepelevkirill.rksi.ui.scenes.schedule

import com.arellomobile.mvp.MvpView
import com.shepelevkirill.rksi.data.core.models.ScheduleModel

interface ScheduleMvpView : MvpView {
    fun setTitle()

    fun showSchedule(schedule: List<ScheduleModel>)
    fun clearSchedule()

    fun showError()
    fun showToast(message: String)

    fun startRefreshing()
    fun stopRefreshing()
}