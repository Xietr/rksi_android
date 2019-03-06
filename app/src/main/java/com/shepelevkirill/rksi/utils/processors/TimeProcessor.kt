package com.shepelevkirill.rksi.utils.processors

import org.threeten.bp.Duration
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter

class TimeProcessor {
    companion object {
        fun getDuration(starTime: LocalTime, endTime: LocalTime): String {
            val formatter = DateTimeFormatter.ofPattern("H:mm")
            val startTimeFormatted = starTime.format(formatter)
            val endTimeFormatted = endTime.format(formatter)

            return "$startTimeFormatted - $endTimeFormatted"
        }

        fun getWaitTime(date: LocalDate, startTime: LocalTime, endTime: LocalTime): String {
            val currentTime = LocalTime.now()
            val currentDate = LocalDate.now()

            var prefix: String
            var hours: Long
            var minutes: Long
            var duration: Duration

            // Если другой день
            if (!currentDate.isEqual(date)) {
                return ""
            }

            // Если пара уже закончилась
            if (currentTime.isAfter(endTime)) {
                return "Пара закончилась!"
            }

            if (currentTime.isBefore(startTime)) {
                // Если пара еще не началась
                prefix = "Начало"
                duration = Duration.between(currentTime, startTime)
            } else {
                // Если пара уже началась
                prefix = "Конец"
                duration = Duration.between(currentTime, endTime)
            }

            hours = duration.toHours()
            minutes = duration.minusHours(hours).toMinutes()
            val seconds = duration.minusHours(hours).minusMinutes(minutes).seconds

            var hoursStr = ""
            if (hours > 0)
                hoursStr = "$hours ч. "

            var minutesStr = ""
            if (minutes > 0)
                minutesStr = "$minutes мин. "

            var secondsStr = ""
            if (seconds > 0)
                secondsStr = "$seconds сек."

            return "$prefix через $hoursStr$minutesStr"
        }

        fun getSubjectStatus(date: LocalDate, startTime: LocalTime, endTime: LocalTime): SubjectStatus {
            val currentTime = LocalTime.now()
            val currentDate = LocalDate.now()

            if (!currentDate.isEqual(date)) {
                return SubjectStatus.ANOTHER_DAY
            }

            if (currentTime.isAfter(endTime)) {
                return SubjectStatus.GONE
            }

            if (currentTime.isBefore(startTime)) {
                return SubjectStatus.WILL_BE
            }

            if (currentTime.isAfter(startTime) && currentTime.isBefore(endTime)) {
                return SubjectStatus.IS_GOING
            }

            return SubjectStatus.NONE
        }
    }

    enum class SubjectStatus {
        NONE,
        ANOTHER_DAY,
        WILL_BE,
        IS_GOING, // Когда пара идет
        GONE, // Пара прошла
    }
}