package com.shepelevkirill.rksi.model.impl.repository
import com.shepelevkirill.rksi.model.core.models.GroupsModel
import com.shepelevkirill.rksi.model.core.models.ScheduleModel
import com.shepelevkirill.rksi.model.core.models.SchedulesModel
import com.shepelevkirill.rksi.model.core.models.TeachersModel
import com.shepelevkirill.rksi.model.core.repository.ScheduleRepository
import com.shepelevkirill.rksi.model.impl.network.Api
import io.reactivex.Observable
import io.reactivex.Single

class ScheduleRepositoryImpl(private val api: Api) : ScheduleRepository {

    override fun getGroups(): Single<List<String>> {
        return api.getGroups()
    }

    override fun getTeachers(): Single<List<String>> {
        return api.getTeachers()
    }

    override fun getScheduleForGroup(group: String): Single<SchedulesModel> {
        var index = -1
            TODO()
    }

    override fun getScheduleForTeacher(teacher: String): Single<SchedulesModel> {
        TODO("")
    }

    override fun getScheduleForGroup(index: Int): Single<SchedulesModel> {
        return api.getScheduleForGroup(index)
    }

    override fun getScheduleForTeacher(index: Int): Single<SchedulesModel> {
        return api.getScheduleForTeacher(index)
    }

}