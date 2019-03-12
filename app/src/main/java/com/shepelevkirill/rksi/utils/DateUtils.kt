package com.shepelevkirill.rksi.utils

import org.threeten.bp.LocalDate

fun LocalDate.isToday() = this.isEqual(LocalDate.now())