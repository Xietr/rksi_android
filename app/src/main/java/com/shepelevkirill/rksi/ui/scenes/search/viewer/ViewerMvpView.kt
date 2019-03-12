package com.shepelevkirill.rksi.ui.scenes.search.viewer

import com.arellomobile.mvp.MvpView
import com.shepelevkirill.rksi.data.core.models.ScheduleModel

interface ViewerMvpView : MvpView {
    fun getSearchData()
    fun addSchedule(schedule: List<ScheduleModel>)
    fun showToast(message: String)
}