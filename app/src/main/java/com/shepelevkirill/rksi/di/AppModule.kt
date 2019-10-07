package com.shepelevkirill.rksi.di

import android.content.Context
import com.shepelevkirill.rksi.ui.scenes.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {
    @Provides
    @Singleton
    fun provideApplication(): App.Companion =
        App

    @Provides
    @Singleton
    fun provideContext(app: App.Companion): Context = app.applicationContext!!
}