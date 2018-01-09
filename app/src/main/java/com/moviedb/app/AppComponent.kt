package com.moviedb.app

import com.moviedb.data.DataModule
import com.moviedb.domain.MovieRepository
import com.moviedb.domain.schedulers.RxSchedulers
import com.moviedb.ui.common.ActivitiesModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        DataModule::class,
        ActivitiesModule::class,
        AndroidSupportInjectionModule::class
))
interface AppComponent: AndroidInjector<MovieDbApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance fun application(app: MovieDbApp): Builder
        fun build(): AppComponent
    }

    fun rxSchedulers(): RxSchedulers

    fun repository(): MovieRepository
}