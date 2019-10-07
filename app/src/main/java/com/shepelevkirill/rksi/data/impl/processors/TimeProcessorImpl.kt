package com.shepelevkirill.rksi.data.impl.processors

import com.shepelevkirill.rksi.data.core.processors.TimeProcessor
import com.shepelevkirill.rksi.data.core.processors.TimeProcessor.IntervalStatus
import com.shepelevkirill.rksi.data.core.processors.TimeProcessor.WaitTime
import org.threeten.bp.Duration
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

class TimeProcessorImpl : TimeProcessor {
    override fun getWaitTime(date: LocalDate, startTime: LocalTime, endTime: LocalTime): WaitTime? {
        val currentTime = LocalTime.now()

        var duration: Duration = Duration.ZERO
        val hours: Long
        val minutes: Long

        val intervalStatus = getIntervalStatus(date, startTime, endTime)

        if (intervalStatus == IntervalStatus.WILL_BE) {
            duration = Duration.between(currentTime, startTime)
        }

        if (intervalStatus == IntervalStatus.IS_GOING) {
            duration = Duration.between(currentTime, endTime)
        }

        hours = duration.toHours()
        minutes = duration.minusHours(hours).toMinutes()

        return when (intervalStatus) {
            IntervalStatus.NONE -> throw Exception("None as interval status")
            IntervalStatus.ANOTHER_DAY -> null
            IntervalStatus.WILL_BE -> WaitTime(WaitTime.Prefix.WILL_START, hours, minutes)
            IntervalStatus.IS_GOING -> WaitTime(WaitTime.Prefix.WILL_END, hours, minutes)
            IntervalStatus.GONE -> WaitTime(WaitTime.Prefix.END, hours, minutes)
        }
    }

    override fun getIntervalStatus(
        date: LocalDate,
        startTime: LocalTime,
        endTime: LocalTime
    ): IntervalStatus {
        val currentTime = LocalTime.now()
        val currentDate = LocalDate.now()

        if (!currentDate.isEqual(date)) {
            return IntervalStatus.ANOTHER_DAY
        }

        if (currentTime.isAfter(endTime)) {
            return IntervalStatus.GONE
        }

        if (currentTime.isBefore(startTime)) {
            return IntervalStatus.WILL_BE
        }

        if (currentTime.isAfter(startTime) && currentTime.isBefore(endTime)) {
            return IntervalStatus.IS_GOING
        }

        return IntervalStatus.NONE
    }

}