package com.shepelevkirill.rksi.ui.scenes.search.viewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.shepelevkirill.rksi.MvpFragment
import com.shepelevkirill.rksi.R
import com.shepelevkirill.rksi.data.core.enums.SearchType
import com.shepelevkirill.rksi.data.core.models.ScheduleModel
import com.shepelevkirill.rksi.ui.adapters.ScheduleAdapter
import com.shepelevkirill.rksi.ui.decorators.StickHeaderItemDecoration
import com.shepelevkirill.rksi.ui.scenes.schedule.ScheduleFragment
import kotlinx.android.synthetic.main.activity_bottom_navigation.*
import kotlinx.android.synthetic.main.fragment_search_viewer.*

class ViewerFragment : MvpFragment(), ViewerMvpView {
    @InjectPresenter
    lateinit var presenter: ViewerPresenter

    private var searchType: SearchType = SearchType.NONE
    private var searchFor: String = ""

    private lateinit var scheduleAdapter: ScheduleAdapter
    //    private val scheduleAdapter by lazy {
//        ScheduleAdapter(searchType)
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val args = ViewerFragmentArgs.fromBundle(it)
            searchFor = args.searchFor
            searchType = args.searchType
        }

        scheduleAdapter = ScheduleAdapter(searchType)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_viewer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        activity?.toolbar?.title = searchFor
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    private fun setupRecyclerView() {
        scheduleRecyclerView.apply {
            adapter = scheduleAdapter
            layoutManager = LinearLayoutManager(view!!.context)
            setHasFixedSize(true)
            addItemDecoration(StickHeaderItemDecoration(adapter as ScheduleAdapter))
        }
    }

    override fun addSchedule(schedule: List<ScheduleModel>) {
        schedule.forEach {
            this.scheduleAdapter.add(it.date)
            this.scheduleAdapter.add(it.schedule)
        }
    }

    override fun getSearchData() {
        return presenter.onGetSearchData(searchType, searchFor)
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance(searchType: SearchType, searchFor: String): ViewerFragment {
            val bundle = Bundle()

            bundle.putSerializable("SearchType", searchType)
            bundle.putString("SearchFor", searchFor)

            val fragment = ViewerFragment()
            fragment.arguments = bundle

            return fragment
        }
    }
}