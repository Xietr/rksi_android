package com.shepelevkirill.rksi.ui.scenes.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.arellomobile.mvp.presenter.InjectPresenter
import com.shepelevkirill.rksi.MvpFragment
import com.shepelevkirill.rksi.R
import com.shepelevkirill.rksi.data.core.enums.SearchType
import com.shepelevkirill.rksi.utils.setVisibility
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : MvpFragment(), SearchMvpView {
    @InjectPresenter
    lateinit var presenter: SearchPresenter

    private val groups = ArrayList<String>()
    private val groupsAdapter by lazy {
        ArrayAdapter<String>(context!!, android.R.layout.simple_spinner_dropdown_item, groups)
    }

    private val teachers = ArrayList<String>()
    private val teachersAdapter by lazy {
        ArrayAdapter<String>(context!!, android.R.layout.simple_spinner_dropdown_item, teachers)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupGroupsSpinner()
        setupTeachersSpinner()
        setupSearchButtons()
    }

    override fun onResume() {
        super.onResume()
        activity?.title = "Поиск"
    }

    private fun setupGroupsSpinner() {
        groupSelector.setTitle("Выберите группу")
        groupSelector.setPositiveButton("Back")
        groupsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        groupSelector.apply {
            adapter = groupsAdapter
        }
    }

    private fun setupTeachersSpinner() {
        teacherSelector.setTitle("Выберите преподавателя")
        teacherSelector.setPositiveButton("Back")
        teachersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        teacherSelector.apply {
            adapter = teachersAdapter
        }
    }

    private fun setupSearchButtons() {
        findGroup.setOnClickListener {
            presenter.onFindForGroup(groupSelector.selectedItem.toString())
        }
        findTeacher.setOnClickListener {
            presenter.onFindForTeacher(teacherSelector.selectedItem.toString())
        }
    }

    override fun addGroups(groups: List<String>) {
        this.groups.addAll(groups)
        groupsAdapter.notifyDataSetChanged()
    }

    override fun addTeachers(teachers: List<String>) {
        this.teachers.addAll(teachers)
        teachersAdapter.notifyDataSetChanged()
    }

    override fun openViewerFragment(searchType: SearchType, searchFor: String) {
        val activity = SearchFragmentDirections.actionSearchToViewer(searchFor, searchType)
        findNavController().navigate(activity)
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun setIsGroupProgressBarVisible(isVisible: Boolean) {
        settingsGroupProgressBar.setVisibility(isVisible)
    }

    override fun setIsTeacherProgressBarVisible(isVisible: Boolean) {
        teacherProgressBar.setVisibility(isVisible)
    }
}