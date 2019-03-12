package com.shepelevkirill.rksi.data.core.processors

import org.threeten.bp.LocalDate

interface DateProcessor {
    fun getDate(date: LocalDate): String
}