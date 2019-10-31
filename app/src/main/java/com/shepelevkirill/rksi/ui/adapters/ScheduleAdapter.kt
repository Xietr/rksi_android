package com.shepelevkirill.rksi.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shepelevkirill.rksi.R
import com.shepelevkirill.rksi.data.core.enums.SearchType
import com.shepelevkirill.rksi.data.core.models.SubjectModel
import com.shepelevkirill.rksi.data.core.processors.DateProcessor
import com.shepelevkirill.rksi.data.core.processors.SubjectProcessor
import com.shepelevkirill.rksi.data.core.processors.TimeProcessor
import com.shepelevkirill.rksi.data.core.processors.TimeProcessor.IntervalStatus
import com.shepelevkirill.rksi.ui.decorators.StickHeaderItemDecoration
import com.shepelevkirill.rksi.ui.scenes.App
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.item_date.view.*
import kotlinx.android.synthetic.main.item_subject.view.*
import org.threeten.bp.LocalDate
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ScheduleAdapter(private val searchType: SearchType) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    StickHeaderItemDecoration.StickyHeaderInterface {

    @Inject
    lateinit var subjectProcessor: SubjectProcessor
    @Inject
    lateinit var dateProcessor: DateProcessor
    @Inject
    lateinit var timeProcessor: TimeProcessor

    private val data: ArrayList<Any> = ArrayList()

    init {
        App.appComponent.inject(this)
    }

    override fun getItemCount(): Int {
        return data.count()
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is LocalDate -> ViewHolderType.DATE.id
            is SubjectModel -> ViewHolderType.SUBJECT.id
            else -> throw NoSuchElementException("Can't associate data with view")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewHolderType.SUBJECT.id -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_subject, parent, false)
                SubjectViewHolder(view)
            }
            ViewHolderType.DATE.id -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_date, parent, false)
                DateViewHolder(view)
            }
            else -> throw NoSuchElementException("Can't associate data with view!")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SubjectViewHolder -> holder.bind(data[position] as SubjectModel)
            is DateViewHolder -> holder.bind(data[position] as LocalDate)
            else -> throw NoSuchElementException("Can't associate data with view")
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        when (holder) {
            is SubjectViewHolder -> holder.unbind()
            is DateViewHolder -> holder.unbind()
        }
    }

    override fun getHeaderPositionForItem(itemPosition: Int): Int {
        var headerPosition = itemPosition
        do {
            if (this.isHeader(headerPosition)) {
                break
            }
            headerPosition--
        } while (headerPosition >= 0)
        return headerPosition
    }

    override fun getHeaderLayout(headerPosition: Int): Int =
        R.layout.item_date

    override fun bindHeaderData(header: View, headerPosition: Int) {
        var localDate = 0

        if (headerPosition >= 0) {
            if (data[headerPosition] is LocalDate) {
                localDate = headerPosition
            }

            header.date.text = dateProcessor.getDate(data[localDate] as LocalDate)
        }
    }

    override fun isHeader(itemPosition: Int): Boolean {
        return data[itemPosition] is LocalDate
    }

    fun add(subject: SubjectModel) {
        data.add(subject)
        notifyDataSetChanged()
    }

    fun add(schedule: List<SubjectModel>) {
        schedule.forEach {
            data.add(it)
        }
        notifyDataSetChanged()
    }

    fun add(date: LocalDate) {
        data.add(date)
        notifyDataSetChanged()
    }

    fun get(position: Int): Any {
        return data[position]
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    fun refresh() {
        notifyDataSetChanged()
    }

    enum class ViewHolderType(val id: Int) {
        NONE(0),
        DATE(1),
        SUBJECT(2)
    }

    inner class DateViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private var updater: Disposable? = null

        fun bind(date: LocalDate) {
            render(date)
            startUpdater(date)
        }

        fun unbind() {
            updater?.dispose()
            updater = null
        }

        // Calls once on create
        private fun render(date: LocalDate) {
            updateDate(date)
        }

        // Calls parallel
        private fun update(date: LocalDate) {
            updateDate(date)
        }

        private fun startUpdater(date: LocalDate) {
            if (updater != null) return

            update(date)
            updater = Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { update(date) }
        }

        private fun updateDate(date: LocalDate) {
            view.date.text = dateProcessor.getDate(date)
        }
    }

    inner class SubjectViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private var updater: Disposable? = null
        private var statusColor: Int = 0

        fun bind(subject: SubjectModel) {
            render(subject)
            startUpdater(subject)
        }

        fun unbind() {
            updater?.dispose()
            updater = null
        }

        // Calls once
        private fun render(subject: SubjectModel) {
            view.subject.text = subjectProcessor.processSubjectName(subject)
            view.startTime.text = subjectProcessor.processStartTime(subject)
            view.endTime.text = subjectProcessor.processEndTime(subject)
            view.cabinet.text = subjectProcessor.processCabinet(subject)

            view.who.text = when (searchType) {
                SearchType.BY_GROUP -> subjectProcessor.processTeacher(subject)
                SearchType.BY_TEACHER -> subjectProcessor.processGroup(subject)
                else -> throw NoSuchElementException("Can't associate search type with data")
            }
        }

        // Calls parallel
        private fun update(subject: SubjectModel) {
            updateStatusColor(subject)

            updateWaitTime(subject)
            updateStatusLine()
        }

        private fun startUpdater(subject: SubjectModel) {
            if (updater != null) return
            update(subject)
            updater = Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { update(subject) }
        }

        private fun updateWaitTime(subject: SubjectModel) {
            val waitTime =
                timeProcessor.getWaitTime(subject.date, subject.startTime, subject.endTime)
            if (waitTime == null) {
                view.swaitTime.visibility = View.GONE
            } else {
                view.swaitTime.visibility = View.VISIBLE
                view.swaitTime.text = waitTime.toString()
                view.swaitTime.setTextColor(statusColor)
            }
        }

        private fun updateStatusLine() {
            view.statusLine.setBackgroundColor(statusColor)
        }

        private fun updateStatusColor(subject: SubjectModel) {
            val status =
                timeProcessor.getIntervalStatus(subject.date, subject.startTime, subject.endTime)
            val resources = App.applicationContext!!.resources
            statusColor = when (status) {
                IntervalStatus.ANOTHER_DAY -> resources.getColor(R.color.colorSubjectAnotherDay)
                IntervalStatus.WILL_BE -> resources.getColor(R.color.colorSubjectWillBe)
                IntervalStatus.IS_GOING -> resources.getColor(R.color.colorSubjectGoing)
                IntervalStatus.GONE -> resources.getColor(R.color.colorSubjectGone)
                else -> throw NoSuchElementException("Can't associate color with status of subject")
            }
        }
    }
}