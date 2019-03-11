package com.shepelevkirill.rksi.data.impl.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.shepelevkirill.rksi.data.core.repository.NetworkRepository

class NetworkRepositoryImpl(private val context: Context) : NetworkRepository {
    override fun isNetworkAvailable(): Boolean {
        var isConnected = false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }
}