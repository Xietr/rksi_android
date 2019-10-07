package com.shepelevkirill.rksi.ui.scenes.settings

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.shepelevkirill.rksi.data.core.repository.PreferencesRepository
import com.shepelevkirill.rksi.data.core.repository.ScheduleRepository
import com.shepelevkirill.rksi.ui.scenes.App
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class SettingsPresenter : MvpPresenter<SettingsMvpView>() {
    @Inject
    lateinit var scheduleRepository: ScheduleRepository
    @Inject
    lateinit var preferencesRepository: PreferencesRepository

    private var groupsLoader: Disposable? = null

    init {
        App.appComponent.inject(this)
    }

    fun onResume() {
        loadGroups()
    }

    fun onGroupSelected(group: String) {
        viewState.showToast("Группа сохранена: $group")

        preferencesRepository.putSelectedGroup(group)
    }

    override fun onDestroy() {
        super.onDestroy()
        groupsLoader?.dispose()
    }

    private fun loadGroups() {
        viewState.clearGroups()
        scheduleRepository.getGroups()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<String>> {
                override fun onSuccess(t: List<String>) {
                    viewState.showGroups(t)

                    val selectedGroup = preferencesRepository.getSelectedGroup()
                    viewState.setSelection(selectedGroup)
                    viewState.setIsGroupProgressBarVisible(false)
                }

                override fun onSubscribe(d: Disposable) {
                    viewState.setIsGroupProgressBarVisible(true)

                    groupsLoader = d
                }

                override fun onError(e: Throwable) {
                    viewState.setIsGroupProgressBarVisible(false)
                    viewState.showToast("Ошибка сети!")
                }

            })
    }

}