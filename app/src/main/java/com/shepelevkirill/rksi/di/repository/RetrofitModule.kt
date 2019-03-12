package com.shepelevkirill.rksi.di.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.shepelevkirill.rksi.data.core.repository.NetworkRepository
import com.shepelevkirill.rksi.di.AppModule
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [(DeserializersModule::class), (AppModule::class), (RepositoryModule::class)])
object RetrofitModule {

    @Provides
    @Singleton
    fun provideRetrofit(callAdapterFactory: CallAdapter.Factory, converterFactory: Converter.Factory, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://rksi.ru/export/")
            .addCallAdapterFactory(callAdapterFactory)
            .addConverterFactory(converterFactory)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideCallAdapterFactory(): CallAdapter.Factory {
        return RxJava2CallAdapterFactory.create()
    }

    @Provides
    @Singleton
    fun provideConverterFactory(gson: Gson): Converter.Factory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideGson(
        localTimeDeserializer: JsonDeserializer<LocalTime>,
        localDateDeserializer: JsonDeserializer<LocalDate>
    ): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(LocalTime::class.java, localTimeDeserializer)
        gsonBuilder.registerTypeAdapter(LocalDate::class.java, localDateDeserializer)
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideClient(interceptor: Interceptor, cache: Cache, networkRepository: NetworkRepository): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor {
                var request = it.request()

                request = if (networkRepository.isNetworkAvailable()) {
                    request.newBuilder().header("Cache-Control", "public, max-age=5").build()
                } else {
                    request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=${60 * 60 * 24 * 7}").build()
                }
                it.proceed(request)
            }
            .addInterceptor(interceptor).build()

        return client
    }

    @Provides
    @Singleton
    fun provideCache(context: Context): Cache {
        val cache = Cache(context.cacheDir, (1024 * 1024 * 10).toLong())
        return cache
    }

    @Provides
    @Singleton
    fun provideInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return interceptor
    }
}