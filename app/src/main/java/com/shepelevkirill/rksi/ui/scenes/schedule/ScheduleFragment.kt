package com.shepelevkirill.rksi.ui.scenes.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.shepelevkirill.rksi.App
import com.shepelevkirill.rksi.MvpFragment
import com.shepelevkirill.rksi.R
import com.shepelevkirill.rksi.data.core.models.ScheduleModel
import com.shepelevkirill.rksi.di.AppComponent
import com.shepelevkirill.rksi.ui.adapters.ScheduleAdapter
import kotlinx.android.synthetic.main.fragment_schedule.*

class ScheduleFragment : MvpFragment(), ScheduleMvpView {

    // TODO CAN BE PRIVATE?
    @InjectPresenter
    lateinit var presenter: SchedulePresenter

    private val adapter: ScheduleAdapter = ScheduleAdapter()
    private val layoutManager: LinearLayoutManager = LinearLayoutManager(App.applicationContext)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSwipeRefreshLayout()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun setupRecyclerView() {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.setOnScrollListener(onScrollListener)
    }

    private fun setupSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            presenter.onRefresh()
        }
    }

    private val onScrollListener = object: RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            presenter.onScrolled(recyclerView)
        }
    }

    override fun setTitle(title: String) {
        
    }

    override fun showSchedule(schedule: List<ScheduleModel>) {
        schedule.forEach {
            adapter.add(it.date)
            adapter.add(it.schedule)
        }
    }

    override fun clearSchedule() {
        adapter.clear()
    }

    override fun showError() {
        TODO("SHOW ERROR")
    }

    override fun stopRefreshing() {
        swipeRefreshLayout.isRefreshing = false
    }
}