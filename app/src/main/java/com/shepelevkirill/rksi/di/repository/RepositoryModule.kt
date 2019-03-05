package com.shepelevkirill.rksi.di.repository

import com.shepelevkirill.rksi.data.core.repository.ScheduleRepository
import com.shepelevkirill.rksi.data.impl.network.Api
import com.shepelevkirill.rksi.data.impl.repository.ScheduleRepositoryImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [(RetrofitModule::class)])
object RepositoryModule {

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Provides
    @Singleton
    fun provideScheduleRepository(api: Api): ScheduleRepository = ScheduleRepositoryImpl(api)
}