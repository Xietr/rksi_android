package com.shepelevkirill.rksi.di

import com.shepelevkirill.rksi.di.repository.DeserializersModule
import com.shepelevkirill.rksi.di.repository.RepositoryModule
import com.shepelevkirill.rksi.di.repository.RetrofitModule
import com.shepelevkirill.rksi.ui.scenes.schedule.SchedulePresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (RepositoryModule::class)])
interface AppComponent {

    fun inject(presenter: SchedulePresenter)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
        fun appModule(app: AppModule): Builder
        fun deserializersModule(deserializers: DeserializersModule): Builder
        fun retrofitModule(retrofit: RetrofitModule): Builder
        fun repositoryModule(repository: RepositoryModule): Builder
    }
}