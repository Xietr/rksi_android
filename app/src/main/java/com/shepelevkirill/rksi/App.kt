package com.shepelevkirill.rksi

import android.app.Application
import android.content.Context
import com.jakewharton.threetenabp.AndroidThreeTen

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        App.applicationContext = applicationContext
        AndroidThreeTen.init(this);
    }

    companion object {
        var applicationContext: Context? = null
            private set
    }
}