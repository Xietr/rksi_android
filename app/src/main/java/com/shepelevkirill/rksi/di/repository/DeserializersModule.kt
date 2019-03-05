package com.shepelevkirill.rksi.di.repository

import com.google.gson.JsonDeserializer
import com.shepelevkirill.rksi.data.impl.deserializers.LocalDateDeserializer
import com.shepelevkirill.rksi.data.impl.deserializers.LocalTimeDeserializer
import dagger.Module
import dagger.Provides
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import javax.inject.Singleton

@Module
object DeserializersModule {

    @Provides
    @Singleton
    fun provideLocalDateDeserializer(): JsonDeserializer<LocalDate> {
        return LocalDateDeserializer()
    }

    @Provides
    @Singleton
    fun provideLocalTimeDeserializer(): JsonDeserializer<LocalTime> {
        return LocalTimeDeserializer()
    }

}