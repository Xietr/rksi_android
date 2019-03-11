package com.shepelevkirill.rksi.ui.scenes.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.crashlytics.android.Crashlytics
import com.github.paolorotolo.appintro.ISlidePolicy
import com.github.paolorotolo.appintro.model.SliderPage
import com.shepelevkirill.rksi.App
import com.shepelevkirill.rksi.R
import com.shepelevkirill.rksi.data.core.repository.PreferencesRepository
import com.shepelevkirill.rksi.data.core.repository.ScheduleRepository
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_intro_group_selection.*
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

class IntroGroupSelectionFragment : Fragment() {
    @Inject lateinit var scheduleRepository: ScheduleRepository
    @Inject lateinit var preferencesRepository: PreferencesRepository

    private val data: ArrayList<String> = ArrayList()
    private val adapter by lazy {
        ArrayAdapter<String>(context!!, android.R.layout.simple_spinner_dropdown_item, data)
    }
    private var groupsLoader: Disposable? = null

    init {
        App.appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_intro_group_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        loadContent()
    }

    override fun onDestroy() {
        super.onDestroy()
        groupsLoader?.dispose()
    }

    fun add(groups: List<String>) {
        data.addAll(groups)
        adapter.notifyDataSetChanged()
    }

    private fun loadContent() {
        scheduleRepository.getGroups()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: SingleObserver<List<String>> {
                override fun onSuccess(t: List<String>) {
                    add(t)
                }

                override fun onSubscribe(d: Disposable) {
                    groupsLoader = d
                }

                override fun onError(e: Throwable) {
                    Crashlytics.logException(e)
                }
            })
    }
}