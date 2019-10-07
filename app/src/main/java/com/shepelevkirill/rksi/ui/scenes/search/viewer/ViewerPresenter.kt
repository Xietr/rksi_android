package com.shepelevkirill.rksi.ui.scenes.search.viewer

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.shepelevkirill.rksi.data.core.enums.SearchType
import com.shepelevkirill.rksi.data.core.models.ScheduleModel
import com.shepelevkirill.rksi.data.core.repository.ScheduleRepository
import com.shepelevkirill.rksi.ui.scenes.App
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class ViewerPresenter : MvpPresenter<ViewerMvpView>() {
    @Inject
    lateinit var scheduleRepository: ScheduleRepository

    private var searchType: SearchType = SearchType.NONE
    private var searchFor: String = ""

    private var scheduleLoader: Disposable? = null

    init {
        App.appComponent.inject(this)
    }

    fun onResume() {
        viewState.getSearchData()
        loadSchedule()
    }

    private fun loadSchedule() {
        if (scheduleLoader != null)
            return

        getScheduleLoader()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<ScheduleModel>> {
                override fun onSuccess(t: List<ScheduleModel>) {
                    viewState.addSchedule(t)
                    scheduleLoader?.dispose()
                    scheduleLoader = null
                }

                override fun onSubscribe(d: Disposable) {
                    scheduleLoader = d
                }

                override fun onError(e: Throwable) {
                    viewState.showToast("Ошибка сети!")
                    scheduleLoader?.dispose()
                    scheduleLoader = null
                }

            })
    }

    fun onGetSearchData(searchType: SearchType, searchFor: String) {
        this.searchType = searchType
        this.searchFor = searchFor
    }

    private fun getScheduleLoader(): Single<List<ScheduleModel>> {
        return if (searchType == SearchType.BY_GROUP)
            scheduleRepository.getScheduleForGroup(searchFor)
        else {
            scheduleRepository.getScheduleForTeacher(searchFor)
        }
    }
}