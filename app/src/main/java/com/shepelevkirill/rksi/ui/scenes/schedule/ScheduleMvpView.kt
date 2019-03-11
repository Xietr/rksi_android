package com.shepelevkirill.rksi.ui.scenes.schedule

import com.arellomobile.mvp.MvpView
import com.shepelevkirill.rksi.data.core.models.ScheduleModel
import com.shepelevkirill.rksi.data.core.models.SubjectModel
import org.threeten.bp.LocalDate

interface ScheduleMvpView : MvpView {
    fun setTitle(title: String)

    fun showSchedule(schedule: List<ScheduleModel>)
    fun clearSchedule()

    fun showError()
    fun showToast(message: String)

    fun stopRefreshing()
}