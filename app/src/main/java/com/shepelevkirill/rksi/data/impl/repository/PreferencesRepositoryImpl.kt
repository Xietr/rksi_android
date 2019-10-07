package com.shepelevkirill.rksi.data.impl.repository

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.shepelevkirill.rksi.data.core.enums.ApplicationMode
import com.shepelevkirill.rksi.data.core.repository.PreferencesRepository

class PreferencesRepositoryImpl(context: Context) : PreferencesRepository {
    private var preferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    companion object {
        const val SELECTED_GROUP_KEY = "GROUP"
        const val SELECTED_GROUP_DEFAULT = "ПОКС-11"

        const val SELECTED_TEACHER_KEY = "TEACHER"
        const val SELECTED_TEACHER_DEFAULT = "TEACHER_UNDEFINED"

        const val MODE_KEY = "MODE"
        val MODE_DEFAULT = ApplicationMode.NONE.id

        const val INTRO_SCREEN_SHOWN_KEY = "INTRO_SCREEN_SHOWN"
        const val INTRO_SCREEN_SHOWN_DEFAULT = false
    }

    override fun putSelectedGroup(group: String) {
        preferences.edit()
            .putString(SELECTED_GROUP_KEY, group)
            .apply()
    }

    override fun getSelectedGroup(): String {
        return preferences.getString(SELECTED_GROUP_KEY, SELECTED_GROUP_DEFAULT)!!
    }

    override fun putSelectedTeacher(teacher: String) {
        preferences.edit()
            .putString(SELECTED_TEACHER_KEY, teacher)
            .apply()
    }

    override fun getSelectedTeacher(): String {
        return preferences.getString(SELECTED_TEACHER_KEY, SELECTED_TEACHER_DEFAULT)!!
    }

    override fun putMode(mode: ApplicationMode) {
        preferences.edit()
            .putInt(MODE_KEY, mode.id)
            .apply()
    }

    override fun getMode(): ApplicationMode {
        val id = preferences.getInt(MODE_KEY, MODE_DEFAULT)
        return ApplicationMode.getFromId(id)
    }

    override fun putIsIntroScreenShown(isShown: Boolean) {
        preferences.edit()
            .putBoolean(INTRO_SCREEN_SHOWN_KEY, isShown)
            .apply()
    }

    override fun getIsIntroScreenShown(): Boolean {
        return preferences.getBoolean(INTRO_SCREEN_SHOWN_KEY, INTRO_SCREEN_SHOWN_DEFAULT)
    }

}