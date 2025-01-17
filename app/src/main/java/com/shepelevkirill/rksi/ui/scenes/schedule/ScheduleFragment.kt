package com.shepelevkirill.rksi.ui.scenes.schedule

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
import kotlinx.android.synthetic.main.activity_bottom_navigation.*
import kotlinx.android.synthetic.main.fragment_schedule.*

class ScheduleFragment : MvpFragment(), ScheduleMvpView {
    @InjectPresenter
    lateinit var presenter: SchedulePresenter

    private val scheduleAdapter: ScheduleAdapter = ScheduleAdapter(SearchType.BY_GROUP)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSwipeRefreshLayout()
        setTitle()
    }

    override fun onResume() {
        super.onResume()
        scheduleAdapter.refresh()
        //activity?.title = "Расписание"

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

    private fun setupSwipeRefreshLayout() {
        val color = resources.getColor(R.color.colorSwipeRefreshLayout)
        swipeRefreshLayout.setColorSchemeColors(color)
        swipeRefreshLayout.setOnRefreshListener {
            presenter.onRefresh()
        }
    }

    override fun setTitle() {
        activity?.toolbar?.title = "Расписание"
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showSchedule(schedule: List<ScheduleModel>) {
        schedule.forEach {
            scheduleAdapter.add(it.date)
            scheduleAdapter.add(it.schedule)
        }
    }

    override fun clearSchedule() {
        scheduleAdapter.clear()
    }

    override fun showError() {
        Toast.makeText(context, "Internet Connection Lost", Toast.LENGTH_SHORT).show()
    }

    override fun startRefreshing() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun stopRefreshing() {
        swipeRefreshLayout.isRefreshing = false
    }
}