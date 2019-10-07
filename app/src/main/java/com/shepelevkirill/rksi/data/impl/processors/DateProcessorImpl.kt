package com.shepelevkirill.rksi.data.impl.processors

import com.shepelevkirill.rksi.data.core.processors.DateProcessor
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoUnit
import java.util.*

class DateProcessorImpl : DateProcessor {
    override fun getDate(date: LocalDate): String {
        val today: LocalDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("d MMM, EEEE", Locale("ru"))
        val returningDate = date.format(formatter)
        return when (ChronoUnit.DAYS.between(today, date).toInt()) {
            -1 -> return "Вчера $returningDate"
            0 -> return "Сегодня $returningDate"
            1 -> return "Завтра $returningDate"
            2 -> return "Послезавтра $returningDate"
            else -> returningDate
        }
    }
}