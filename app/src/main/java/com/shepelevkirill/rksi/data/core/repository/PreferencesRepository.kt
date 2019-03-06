package com.shepelevkirill.rksi.data.core.repository

import com.shepelevkirill.rksi.data.core.enums.ApplicationMode

interface PreferencesRepository {
    fun putSelectedGroup(group: String)
    fun getSelectedGroup(): String

    fun putSelectedTeacher(teacher: String): String
    fun getSelectedTeacher(): String

    fun putMode(mode: ApplicationMode)
    fun getMode(): ApplicationMode

    fun putIsIntroScreenShown(isShown: Boolean)
    fun getIsIntroScreenShown(): Boolean
}