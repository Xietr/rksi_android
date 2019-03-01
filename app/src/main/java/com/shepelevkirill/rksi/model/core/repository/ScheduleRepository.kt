package com.shepelevkirill.rksi.model.core.repository

import com.shepelevkirill.rksi.model.core.models.GroupsModel
import com.shepelevkirill.rksi.model.core.models.ScheduleModel
import com.shepelevkirill.rksi.model.core.models.SchedulesModel
import com.shepelevkirill.rksi.model.core.models.TeachersModel
import io.reactivex.Single

interface ScheduleRepository {
    fun getGroups(): Single<List<String>>
    fun getTeachers(): Single<List<String>>

    fun getScheduleForGroup(group: String): Single<SchedulesModel>
    fun getScheduleForTeacher(teacher: String): Single<SchedulesModel>

    fun getScheduleForGroup(index: Int): Single<SchedulesModel>
    fun getScheduleForTeacher(index: Int): Single<SchedulesModel>
}