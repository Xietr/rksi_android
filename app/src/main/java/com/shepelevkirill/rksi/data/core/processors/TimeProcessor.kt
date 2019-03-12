package com.shepelevkirill.rksi.data.core.processors

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

interface TimeProcessor {
    enum class IntervalStatus {
        NONE,
        ANOTHER_DAY,
        WILL_BE,
        IS_GOING, // Когда пара идет
        GONE, // Пара прошла
    }

    class WaitTime(private var prefix: Prefix, private var hours: Long, private var minutes: Long) {
        enum class Prefix {
            NONE,
            WILL_START,
            WILL_END,
            END;

            override fun toString(): String = when (this) {
                NONE -> "NONE"
                WILL_START -> "Начало"
                WILL_END -> "Конец"
                END -> "Пара закончилась"
            }
        }

        override fun toString(): String {
            val hoursStr = if (hours > 0) "$hours ч. " else ""
            val minutesStr = if (minutes > 0) "$minutes мин." else ""
            return "$prefix $hoursStr$minutesStr"
        }
    }

    fun getWaitTime(date: LocalDate, startTime: LocalTime, endTime: LocalTime): WaitTime?
    fun getIntervalStatus(date: LocalDate, startTime: LocalTime, endTime: LocalTime): IntervalStatus
}