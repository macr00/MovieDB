package com.moviedb.ui.common

import com.moviedb.ui.list.MovieListActivity
import com.moviedb.ui.list.MovieListModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector(modules = arrayOf(MovieListModule::class, FragmentsModule::class))
    abstract fun movieListActivity(): MovieListActivity

}