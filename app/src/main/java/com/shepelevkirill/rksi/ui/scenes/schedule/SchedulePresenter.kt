package com.shepelevkirill.rksi.ui.scenes.schedule

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.shepelevkirill.rksi.data.core.models.ScheduleModel
import com.shepelevkirill.rksi.data.core.models.SubjectModel
import com.shepelevkirill.rksi.data.core.processors.DateProcessor
import com.shepelevkirill.rksi.data.core.repository.PreferencesRepository
import com.shepelevkirill.rksi.data.core.repository.ScheduleRepository
import com.shepelevkirill.rksi.ui.adapters.ScheduleAdapter
import com.shepelevkirill.rksi.ui.scenes.App
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.threeten.bp.LocalDate
import java.util.*
import javax.inject.Inject

@InjectViewState
class SchedulePresenter : MvpPresenter<ScheduleMvpView>() {
    @Inject
    lateinit var scheduleRepository: ScheduleRepository
    @Inject
    lateinit var preferencesRepository: PreferencesRepository
    @Inject
    lateinit var dateProcessor: DateProcessor

    private var currentGroup = ""
    private var scheduleLoader: Disposable? = null

    init {
        App.appComponent.inject(this)
    }

    fun onResume() {
        val selectedGroup = preferencesRepository.getSelectedGroup()
        if (selectedGroup != currentGroup) {
            currentGroup = selectedGroup
            loadSchedule()
        }
    }

    override fun onFirstViewAttach() {

    }

    override fun onDestroy() {
        super.onDestroy()
        scheduleLoader?.dispose()
    }

    private fun setTitle(scheduleFor: String) {

    }

    fun onScrolled(recyclerView: RecyclerView) {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val adapter = recyclerView.adapter as ScheduleAdapter

        val lastVisibleItemPos = layoutManager.findFirstCompletelyVisibleItemPosition()
        if (lastVisibleItemPos < 0) {
            return
        }

        val lastVisibleItem = adapter.get(lastVisibleItemPos)

        when (lastVisibleItem) {
            is SubjectModel -> {
                val title = dateProcessor.getDate(lastVisibleItem.date) + " ($currentGroup)"
                viewState.setTitle(title)
            }
            is LocalDate -> {
                val title = dateProcessor.getDate(lastVisibleItem) + " ($currentGroup)"
                viewState.setTitle(title)
            }
            else -> throw NoSuchElementException("Can't associate this with element")
        }
    }

    fun onRefresh() {
        scheduleLoader?.dispose()
        viewState.clearSchedule()
        loadSchedule()
    }

    private fun loadSchedule() {
        if (scheduleLoader != null)
            return

        viewState.clearSchedule()
        scheduleRepository.getScheduleForGroup(currentGroup)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<ScheduleModel>> {
                override fun onSubscribe(d: Disposable) {
                    scheduleLoader = d
                    viewState.startRefreshing()
                }

                override fun onSuccess(t: List<ScheduleModel>) {
                    viewState.showSchedule(t)
                    viewState.stopRefreshing()

                    scheduleLoader?.dispose()
                    scheduleLoader = null
                }

                override fun onError(e: Throwable) {
                    viewState.stopRefreshing()
                    viewState.showToast("Ошибка сети!")

                    scheduleLoader?.dispose()
                    scheduleLoader = null
                }

            })
    }
}