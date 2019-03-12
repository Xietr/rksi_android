package com.shepelevkirill.rksi.data.impl.processors

import com.shepelevkirill.rksi.data.core.models.SubjectModel
import com.shepelevkirill.rksi.data.core.processors.SubjectProcessor
import com.shepelevkirill.rksi.data.core.processors.TimeProcessor
import com.shepelevkirill.rksi.utils.getString

class SubjectProcessorImpl(private val timeProcessor: TimeProcessor) : SubjectProcessor {

    override fun processSubjectName(subject: SubjectModel): String {
        return subject.subject
    }

    override fun processTeacher(subject: SubjectModel): String {
        return subject.teacher
    }

    override fun processGroup(subject: SubjectModel): String {
        return subject.group
    }

    override fun processCabinet(subject: SubjectModel): String = "Кабинет ${subject.cabinet}"

    override fun processStartTime(subject: SubjectModel): String = subject.startTime.getString()

    override fun processEndTime(subject: SubjectModel): String = subject.endTime.getString()

    override fun processWaitTime(subject: SubjectModel): String? {
        return timeProcessor.getWaitTime(subject.date, subject.startTime, subject.endTime)?.toString()
    }
}