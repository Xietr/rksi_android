package com.shepelevkirill.rksi.di

import com.shepelevkirill.rksi.di.repository.DeserializersModule
import com.shepelevkirill.rksi.di.repository.ProcessorsModule
import com.shepelevkirill.rksi.di.repository.RepositoryModule
import com.shepelevkirill.rksi.di.repository.RetrofitModule
import com.shepelevkirill.rksi.ui.adapters.ScheduleAdapter
import com.shepelevkirill.rksi.ui.scenes.intro.IntroActivity
import com.shepelevkirill.rksi.ui.scenes.schedule.SchedulePresenter
import com.shepelevkirill.rksi.ui.scenes.search.SearchPresenter
import com.shepelevkirill.rksi.ui.scenes.search.viewer.ViewerPresenter
import com.shepelevkirill.rksi.ui.scenes.settings.SettingsPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (RepositoryModule::class), (ProcessorsModule::class)])
interface AppComponent {

    fun inject(presenter: SchedulePresenter)
    fun inject(presenter: SearchPresenter)
    fun inject(presenter: ViewerPresenter)
    fun inject(presenter: SettingsPresenter)

    fun inject(activity: IntroActivity)

    fun inject(adapter: ScheduleAdapter)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
        fun appModule(app: AppModule): Builder
        fun deserializersModule(deserializers: DeserializersModule): Builder
        fun retrofitModule(retrofit: RetrofitModule): Builder
        fun repositoryModule(repository: RepositoryModule): Builder
        fun processorsModule(processors: ProcessorsModule): Builder
    }
}