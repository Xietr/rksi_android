package com.shepelevkirill.rksi.data.impl.processors

import com.shepelevkirill.rksi.data.core.processors.DateProcessor
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.util.*


class DateProcessorImpl : DateProcessor {
    override fun getDate(date: LocalDate): String {
            val localDate: LocalDate = LocalDate.now()
            when (date.compareTo(localDate)) {
                0 -> return "Сегодня"
                1 -> return "Завтра"
                2 -> return "Послезавтра"
            }
            val formatter = DateTimeFormatter.ofPattern("d MMM, EEEE", Locale("ru"))
            return date.format(formatter)
    }
}