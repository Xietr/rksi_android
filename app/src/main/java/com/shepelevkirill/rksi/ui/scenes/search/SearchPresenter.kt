package com.shepelevkirill.rksi.ui.scenes.search

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.shepelevkirill.rksi.data.core.enums.SearchType
import com.shepelevkirill.rksi.data.core.repository.ScheduleRepository
import com.shepelevkirill.rksi.ui.scenes.App
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class SearchPresenter : MvpPresenter<SearchMvpView>() {
    @Inject
    lateinit var scheduleRepository: ScheduleRepository
    private var teachersLoader: Disposable? = null
    private var groupsLoader: Disposable? = null

    init {
        App.appComponent.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadTeachers()
        loadGroups()
    }

    fun onFindForGroup(group: String) {
        viewState.openViewerFragment(SearchType.BY_GROUP, group)
    }

    fun onFindForTeacher(teacher: String) {
        viewState.openViewerFragment(SearchType.BY_TEACHER, teacher)
    }

    private fun loadTeachers() {
        if (teachersLoader != null)
            return

        scheduleRepository.getTeachers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<String>> {
                override fun onSuccess(t: List<String>) {
                    viewState.addTeachers(t)

                    teachersLoader?.dispose()
                    teachersLoader = null
                    viewState.setIsTeacherProgressBarVisible(false)
                }

                override fun onSubscribe(d: Disposable) {
                    viewState.setIsGroupProgressBarVisible(true)

                    teachersLoader = d
                }

                override fun onError(e: Throwable) {
                    viewState.setIsTeacherProgressBarVisible(false)

                    viewState.showToast("Ошибка сети!")
                    teachersLoader?.dispose()
                    teachersLoader = null
                }

            })
    }

    private fun loadGroups() {
        if (groupsLoader != null)
            return

        scheduleRepository.getGroups()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<String>> {
                override fun onSuccess(t: List<String>) {
                    viewState.addGroups(t)

                    groupsLoader?.dispose()
                    groupsLoader = null
                    viewState.setIsGroupProgressBarVisible(false)
                }

                override fun onSubscribe(d: Disposable) {
                    viewState.setIsGroupProgressBarVisible(true)

                    groupsLoader = d
                }

                override fun onError(e: Throwable) {
                    viewState.setIsGroupProgressBarVisible(false)

                    groupsLoader?.dispose()
                    groupsLoader = null
                    // TODO SHOW ERROR
                }

            })
    }

}