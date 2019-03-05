package com.shepelevkirill.rksi.data.core.models

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

data class ScheduleModel(
    val date: LocalDate,
    val schedule: List<SubjectModel>
)

data class SubjectModel(
    @SerializedName("tt_gr")
    val group: String,

    @SerializedName("tt_date")
    val date: LocalDate,

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