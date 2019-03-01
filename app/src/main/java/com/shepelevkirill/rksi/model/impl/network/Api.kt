package com.shepelevkirill.rksi.model.impl.network

import com.shepelevkirill.rksi.model.core.models.GroupsModel
import com.shepelevkirill.rksi.model.core.models.ScheduleModel
import com.shepelevkirill.rksi.model.core.models.SchedulesModel
import com.shepelevkirill.rksi.model.core.models.TeachersModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("schedule.php?type=groups")
    fun getGroups(): Single<List<String>>

    @GET("schedule.php?type=teachers")
    fun getTeachers(): Single<List<String>>

    @GET("schedule.php?type=group")
    fun getScheduleForGroup(@Path("item") index: Int): Single<SchedulesModel>

    @GET("schedule.php?type=teacher")
    fun getScheduleForTeacher(@Path("item") index: Int): Single<SchedulesModel>
}