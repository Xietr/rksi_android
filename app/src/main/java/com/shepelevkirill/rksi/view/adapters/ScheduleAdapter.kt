package com.shepelevkirill.rksi.view.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shepelevkirill.rksi.App
import com.shepelevkirill.rksi.R
import com.shepelevkirill.rksi.model.core.models.SubjectModel
import com.shepelevkirill.rksi.view.getString
import com.shepelevkirill.rksi.view.processors.DateProcessor
import com.shepelevkirill.rksi.view.processors.TimeProcessor
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.item_date.view.*
import kotlinx.android.synthetic.main.item_subject.view.*
import org.threeten.bp.LocalDate
import java.util.concurrent.TimeUnit
import javax.security.auth.Subject

class ScheduleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val data: ArrayList<Any> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
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

    override fun getItemCount(): Int {
        return data.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is SubjectViewHolder -> holder.bind(data[position] as SubjectModel)
            is DateViewHolder -> holder.bind(data[position] as LocalDate)
            else -> throw NoSuchElementException("Can't associate data with view")
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        when(holder) {
            is SubjectViewHolder -> holder.unbind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is LocalDate -> ViewHolderType.DATE.id
            is SubjectModel -> ViewHolderType.SUBJECT.id
            else -> throw NoSuchElementException("Can't associate data with view")
        }
    }

    enum class ViewHolderType(val id: Int) {
        NONE(0),
        DATE(1),
        SUBJECT(2)
    }

    inner class DateViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(date: LocalDate) {
            view.date.text = DateProcessor.getDate(date)
        }
    }

    inner class SubjectViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private var updater: Disposable? = null
        private var statusColor: Int = 0

        fun bind(subject: SubjectModel) {
            view.subject.text = subject.subject
            view.startTime.text = subject.startTime.getString()
            view.endTime.text = subject.endTime.getString()
            view.cabinet.text = "Кабинет ${subject.cabinet}"
            view.who.text = subject.teacher

            updateStatusColor(subject)
            updateWaitTime(subject)
            updateStatusLine()

            if (updater == null)
                updater = Observable.interval(1, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.single())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        updateStatusColor(subject)
                        updateWaitTime(subject)
                        updateStatusLine()
                    }
        }

        fun unbind() {
            updater?.dispose()
            updater = null
        }


        fun updateWaitTime(subject: SubjectModel) {
            val waitTime = TimeProcessor.getWaitTime(subject.date, subject.startTime, subject.endTime)
            if (waitTime == "") {
                view.waitTime.visibility = View.GONE
            } else {
                view.waitTime.visibility = View.VISIBLE
                view.waitTime.text = waitTime
                view.waitTime.setTextColor(statusColor)
            }
        }

        fun updateStatusLine() {
            view.statusLine.setBackgroundColor(statusColor)
        }

        fun updateStatusColor(subject: SubjectModel) {
            val status = TimeProcessor.getSubjectStatus(subject.date, subject.startTime, subject.endTime)
            val resources = App.applicationContext!!.resources
            statusColor = when(status) {
                TimeProcessor.SubjectStatus.ANOTHER_DAY -> resources.getColor(R.color.colorSubjectAnotherDay)
                TimeProcessor.SubjectStatus.WILL_BE -> resources.getColor(R.color.colorSubjectWillBe)
                TimeProcessor.SubjectStatus.IS_GOING -> resources.getColor(R.color.colorSubjectGoing)
                TimeProcessor.SubjectStatus.GONE -> resources.getColor(R.color.colorSubjectGone)
                else -> throw NoSuchElementException("Can't associate color with status of subject")
            }
        }
    }


    fun add(subject: SubjectModel) {
        data.add(subject)
        notifyDataSetChanged()
    }

    fun add(date: LocalDate) {
        data.add(date)
        notifyDataSetChanged()
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    fun refresh() {
        notifyDataSetChanged()
    }
}