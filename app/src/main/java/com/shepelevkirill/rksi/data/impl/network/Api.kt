package com.shepelevkirill.rksi.data.impl.network

import com.shepelevkirill.rksi.data.core.models.ScheduleModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("schedule.php?type=groups")
    fun getGroups(): Single<List<String>>

    @GET("schedule.php?type=teachers")
    fun getTeachers(): Single<List<String>>

    @GET("schedule.php?type=group")
    fun getScheduleForGroup(@Query("item") index: Int): Single<List<ScheduleModel>>

    @GET("schedule.php?type=teacher")
    fun getScheduleForTeacher(@Query("item") index: Int): Single<List<ScheduleModel>>
}