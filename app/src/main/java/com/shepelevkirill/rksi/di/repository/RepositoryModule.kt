package com.shepelevkirill.rksi.di.repository

import android.content.Context
import com.shepelevkirill.rksi.data.core.repository.NetworkRepository
import com.shepelevkirill.rksi.data.core.repository.PreferencesRepository
import com.shepelevkirill.rksi.data.core.repository.ScheduleRepository
import com.shepelevkirill.rksi.data.impl.network.Api
import com.shepelevkirill.rksi.data.impl.repository.NetworkRepositoryImpl
import com.shepelevkirill.rksi.data.impl.repository.PreferencesRepositoryImpl
import com.shepelevkirill.rksi.data.impl.repository.ScheduleRepositoryImpl
import com.shepelevkirill.rksi.di.AppModule
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [(RetrofitModule::class), (AppModule::class)])
object RepositoryModule {

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Provides
    @Singleton
    fun provideScheduleRepository(api: Api): ScheduleRepository = ScheduleRepositoryImpl(api)

    @Provides
    @Singleton
    fun providePreferencesRepository(context: Context): PreferencesRepository =
        PreferencesRepositoryImpl(context)

    @Provides
    @Singleton
    fun provideNetworkRepository(context: Context): NetworkRepository =
        NetworkRepositoryImpl(context)
}