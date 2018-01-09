package com.moviedb.ui.common

import com.moviedb.ui.details.MovieDetailActivity
import com.moviedb.ui.list.MovieListActivity
import com.moviedb.ui.list.MovieListModule
import com.moviedb.ui.search.SearchModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector(modules = arrayOf(MovieListModule::class, SearchModule::class, FragmentsModule::class))
    abstract fun movieListActivity(): MovieListActivity

    @ContributesAndroidInjector(modules = arrayOf(FragmentsModule::class))
    abstract fun movieDetailActivity(): MovieDetailActivity
}