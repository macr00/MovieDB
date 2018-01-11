package com.moviedb.ui.common

import com.moviedb.ui.details.MovieDetailsActivity
import com.moviedb.ui.details.MovieDetailsModule
import com.moviedb.ui.list.MovieListActivity
import com.moviedb.ui.list.MovieListModule
import com.moviedb.ui.search.SearchModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector(modules = arrayOf(MovieListModule::class, SearchModule::class, FragmentsModule::class))
    abstract fun movieListActivity(): MovieListActivity

    @ContributesAndroidInjector(modules = arrayOf(MovieDetailsModule::class, FragmentsModule::class))
    abstract fun movieDetailActivity(): MovieDetailsActivity
}