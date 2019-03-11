package com.shepelevkirill.rksi.data.core.enums

enum class ApplicationMode(val id: Int) {
    NONE(0),
    GROUP_MODE(1),
    TEACHER_MODE(2);

    companion object {
        fun getFromId(id: Int): ApplicationMode {
            return when(id) {
                ApplicationMode.GROUP_MODE.id -> ApplicationMode.GROUP_MODE
                ApplicationMode.TEACHER_MODE.id -> ApplicationMode.TEACHER_MODE
                else -> ApplicationMode.NONE
            }
        }
    }
}