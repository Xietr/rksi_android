package com.shepelevkirill.rksi.ui.scenes

import android.app.Application
import android.content.Context
import com.jakewharton.threetenabp.AndroidThreeTen
import com.shepelevkirill.rksi.di.AppComponent
import com.shepelevkirill.rksi.di.AppModule
import com.shepelevkirill.rksi.di.DaggerAppComponent
import com.shepelevkirill.rksi.di.repository.DeserializersModule
import com.shepelevkirill.rksi.di.repository.ProcessorsModule
import com.shepelevkirill.rksi.di.repository.RepositoryModule
import com.shepelevkirill.rksi.di.repository.RetrofitModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Companion.applicationContext = applicationContext
        initAppComponent()
        AndroidThreeTen.init(this)
    }

    private fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule)
            .deserializersModule(DeserializersModule)
            .retrofitModule(RetrofitModule)
            .repositoryModule(RepositoryModule)
            .processorsModule(ProcessorsModule)
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
        var applicationContext: Context? = null
            private set
    }
}