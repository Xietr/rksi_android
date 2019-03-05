package com.shepelevkirill.rksi.data.impl.repository

import com.shepelevkirill.rksi.data.core.models.ScheduleModel
import com.shepelevkirill.rksi.data.core.repository.ScheduleRepository
import com.shepelevkirill.rksi.data.impl.network.Api
import io.reactivex.Single

class ScheduleRepositoryImpl(private val api: Api) : ScheduleRepository {

    override fun getGroups(): Single<List<String>> {
        return api.getGroups()
    }

    override fun getTeachers(): Single<List<String>> {
        return api.getTeachers()
    }

    override fun getScheduleForGroup(group: String): Single<List<ScheduleModel>> {
        return getGroups().flatMap {
            val index = it.indexOf(group)
            if (index == -1) {
                throw NoSuchElementException("Can't find such group for API request!")
            }
            return@flatMap api.getScheduleForGroup(index)
        }
    }

    override fun getScheduleForTeacher(teacher: String): Single<List<ScheduleModel>> {
        return getTeachers().flatMap {
            val index = it.indexOf(teacher)
            if (index == -1) {
                return@flatMap Single.error<List<ScheduleModel>>(NoSuchElementException("Can't find such teacher for API request!"))
            }
            return@flatMap api.getScheduleForTeacher(index)
        }
    }

    override fun getScheduleForGroup(index: Int): Single<List<ScheduleModel>> {
        return api.getScheduleForGroup(index)
    }

    override fun getScheduleForTeacher(index: Int): Single<List<ScheduleModel>> {
        return api.getScheduleForTeacher(index)
    }

}