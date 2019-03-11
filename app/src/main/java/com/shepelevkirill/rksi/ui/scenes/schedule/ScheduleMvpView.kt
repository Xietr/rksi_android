package com.shepelevkirill.rksi.ui.scenes.schedule

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.shepelevkirill.rksi.data.core.models.ScheduleModel

interface ScheduleMvpView : MvpView {
    fun setTitle(title: String)

    fun showSchedule(schedule: List<ScheduleModel>)
    fun clearSchedule()

    fun showError()
    fun showToast(message: String)

    fun stopRefreshing()
}