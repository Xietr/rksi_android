package com.shepelevkirill.rksi.ui.scenes.search

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.shepelevkirill.rksi.data.core.enums.SearchType

interface SearchMvpView : MvpView {
    fun addGroups(groups: List<String>)
    fun addTeachers(teachers: List<String>)

    @StateStrategyType(SkipStrategy::class)
    fun openViewerFragment(searchType: SearchType, searchFor: String)
}