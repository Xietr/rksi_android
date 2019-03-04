package com.shepelevkirill.rksi.model.core.repository

import com.shepelevkirill.rksi.model.core.models.GroupsModel
import com.shepelevkirill.rksi.model.core.models.ScheduleModel
import com.shepelevkirill.rksi.model.core.models.TeachersModel
import io.reactivex.Single

interface ScheduleRepository {
    fun getGroups(): Single<List<String>>
    fun getTeachers(): Single<List<String>>

    fun getScheduleForGroup(group: String): Single<List<ScheduleModel>>
    fun getScheduleForTeacher(teacher: String): Single<List<ScheduleModel>>

    fun getScheduleForGroup(index: Int): Single<List<ScheduleModel>>
    fun getScheduleForTeacher(index: Int): Single<List<ScheduleModel>>
}