package com.shepelevkirill.rksi.data.impl.processors

import com.shepelevkirill.rksi.data.core.models.SubjectModel
import com.shepelevkirill.rksi.data.core.processors.SubjectProcessor
import com.shepelevkirill.rksi.data.core.processors.TimeProcessor
import javax.inject.Inject

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

    override fun processWaitTime(subject: SubjectModel): String? {
        return timeProcessor.getWaitTime(subject.date, subject.startTime, subject.endTime)?.toString()
    }
}