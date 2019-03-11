package com.shepelevkirill.rksi.data.core.repository

interface NetworkRepository {
    fun isNetworkAvailable(): Boolean
}