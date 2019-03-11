package com.shepelevkirill.rksi.ui.scenes.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

    override fun onResume() {
        super.onResume()
        adapter.refresh()
        activity?.setTitle("Расписание")
        presenter.onResume()
    }

    private fun setupRecyclerView() {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(view!!.context)
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
        activity!!.title = title
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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
        Toast.makeText(context, "Internet Connection Lost", Toast.LENGTH_SHORT)
    }

    override fun startRefreshing() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun stopRefreshing() {
        swipeRefreshLayout.isRefreshing = false
    }
}