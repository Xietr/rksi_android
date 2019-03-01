package com.shepelevkirill.rksi.model.core.models

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.LocalTime

data class SchedulesModel(
    val schedules: List<ScheduleModel>
)

data class ScheduleModel(
    val date: LocalDate,
    val schedule: List<SubjectModel>
)

data class SubjectModel(
    @SerializedName("tt_gr")
    val group: String,

    @SerializedName("tt_start")
    val startTime: LocalTime,

    @SerializedName("tt_end")
    val endTime: LocalTime,

    @SerializedName("tt_teacher")
    val teacher: String,

    @SerializedName("tt_cab")
    val cabinet: String,

    @SerializedName("tt_sub")
    val subject: String
)