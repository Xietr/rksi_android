package com.shepelevkirill.rksi

import android.app.Application
import android.content.Context
import com.jakewharton.threetenabp.AndroidThreeTen
import com.shepelevkirill.rksi.di.AppComponent
import com.shepelevkirill.rksi.di.AppModule
import com.shepelevkirill.rksi.di.DaggerAppComponent
import com.shepelevkirill.rksi.di.repository.DeserializersModule
import com.shepelevkirill.rksi.di.repository.RepositoryModule
import com.shepelevkirill.rksi.di.repository.RetrofitModule

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        App.applicationContext = applicationContext
        initAppComponent()
        AndroidThreeTen.init(this);
    }

    fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule)
            .deserializersModule(DeserializersModule)
            .retrofitModule(RetrofitModule)
            .repositoryModule(RepositoryModule)
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
        var applicationContext: Context? = null
            private set
    }
}