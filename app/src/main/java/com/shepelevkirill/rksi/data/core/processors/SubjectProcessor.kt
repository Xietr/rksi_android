package com.shepelevkirill.rksi.data.core.processors

import com.shepelevkirill.rksi.data.core.models.SubjectModel

interface SubjectProcessor {
    fun processSubjectName(subject: SubjectModel): String

    fun processTeacher(subject: SubjectModel): String
    fun processGroup(subject: SubjectModel): String

    fun processWaitTime(subject: SubjectModel): String?
}