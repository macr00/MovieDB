package com.moviedb.app

import com.moviedb.ui.list.MovieListActivity
import com.moviedb.ui.list.MovieListModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AndroidBindingModule {

    @ContributesAndroidInjector(modules = arrayOf(MovieListModule::class))
    abstract fun movieListActivity(): MovieListActivity

}