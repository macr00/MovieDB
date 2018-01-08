package com.moviedb.app

import com.moviedb.ui.list.MovieListFragment
import com.moviedb.ui.list.MovieListModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsModule {

    @ContributesAndroidInjector(modules = arrayOf(MovieListModule::class))
    abstract fun movieListFragment(): MovieListFragment
}