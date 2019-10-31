package com.shepelevkirill.rksi.ui.scenes.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.crashlytics.android.Crashlytics
import com.shepelevkirill.rksi.MvpFragment
import com.shepelevkirill.rksi.R
import com.shepelevkirill.rksi.utils.setVisibility
import kotlinx.android.synthetic.main.activity_bottom_navigation.*
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : MvpFragment(), SettingsMvpView {
    @InjectPresenter
    lateinit var presenter: SettingsPresenter

    private val data = ArrayList<String>()
    private val adapter by lazy {
        ArrayAdapter<String>(context!!, android.R.layout.simple_spinner_dropdown_item, data)
    }
    private var isFirstSelection = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSpinner()
        presenter.onResume()
        activity?.toolbar?.title = "Настройки"
    }

    private fun setupSpinner() {
        groupSelector.setTitle("Выберите группу")
        groupSelector.setPositiveButton("Back")
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        groupSelector.adapter = adapter
        groupSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) = Unit

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (isFirstSelection) {
                    isFirstSelection = false
                    return
                }
                presenter.onGroupSelected(data[position])
            }
        }
    }

    override fun onPause() {
        super.onPause()
        isFirstSelection = true
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun setSelection(group: String) {
        val index = data.indexOf(group)
        if (index < 0) {
            showToast("Ошибка! Изменение групп")
            Crashlytics.logException(NoSuchElementException("Невозможно сопоставить сохраненную в памяти группу и текущий список групп на сервере"))
            return
        }
        groupSelector.setSelection(index)
    }

    override fun showGroups(groups: List<String>) {
        groups.forEach {
            data.add(it)
        }
        adapter.notifyDataSetChanged()
    }

    override fun clearGroups() {
        data.clear()
        adapter.notifyDataSetChanged()
    }

    override fun setIsGroupProgressBarVisible(isVisible: Boolean) {
        settingsGroupProgressBar.setVisibility(isVisible)
    }
}