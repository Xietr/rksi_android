package com.shepelevkirill.rksi.model.core.processors

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

            return "$prefix через $hours ч. $minutes мин."
        }
    }
}