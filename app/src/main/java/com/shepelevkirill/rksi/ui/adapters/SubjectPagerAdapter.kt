package com.shepelevkirill.rksi.ui.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.shepelevkirill.rksi.R
import com.shepelevkirill.rksi.data.core.models.SubjectModel
import com.shepelevkirill.rksi.utils.processors.TimeProcessor
import kotlinx.android.synthetic.main.item_subject_cardview.view.*

class SubjectPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val subjects: ArrayList<SubjectModel> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return Holder.newInstance(subjects[position])
    }

    override fun getCount(): Int {
        return subjects.count()
    }

    fun add(subject: SubjectModel) {
        subjects.add(subject)
        notifyDataSetChanged()
    }

    fun clear() {
        subjects.clear()
        notifyDataSetChanged()
    }

    class Holder : Fragment() {
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.item_subject_cardview, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            val subject = arguments?.getSerializable("subject") as SubjectModel

            view.subject.text = subject.subject
            view.who.text = subject.teacher
            view.duration.text = TimeProcessor.getDuration(subject.startTime, subject.endTime)
            view.cabinet.text = "Кабинет ${subject.cabinet}"
        }

        companion object {
            fun newInstance(subject: SubjectModel): Holder {
                val bundle = Bundle()
                bundle.putSerializable("subject", subject)

                val fragment = Holder()
                fragment.arguments = bundle

                return fragment
            }
        }
    }
}