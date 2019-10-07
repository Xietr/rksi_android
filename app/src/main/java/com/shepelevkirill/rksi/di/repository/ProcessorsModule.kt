package com.shepelevkirill.rksi.di.repository

import com.shepelevkirill.rksi.data.core.processors.DateProcessor
import com.shepelevkirill.rksi.data.core.processors.SubjectProcessor
import com.shepelevkirill.rksi.data.core.processors.TimeProcessor
import com.shepelevkirill.rksi.data.impl.processors.DateProcessorImpl
import com.shepelevkirill.rksi.data.impl.processors.SubjectProcessorImpl
import com.shepelevkirill.rksi.data.impl.processors.TimeProcessorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ProcessorsModule {
    @Provides
    @Singleton
    fun provideTimeProcessor(): TimeProcessor = TimeProcessorImpl()

    @Provides
    @Singleton
    fun provideDateProcessor(): DateProcessor = DateProcessorImpl()

    @Provides
    @Singleton
    fun provideSubjectProcessor(timeProcessor: TimeProcessor): SubjectProcessor =
        SubjectProcessorImpl(timeProcessor)
}