package com.moviedb.ui.common

import com.moviedb.ui.details.MovieDetailsFragment
import com.moviedb.ui.list.MovieListFragment
import com.moviedb.ui.list.MovieListModule
import com.moviedb.ui.search.SearchFragment
import com.moviedb.ui.search.SearchModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsModule {

    @ContributesAndroidInjector(modules = arrayOf(MovieListModule::class))
    abstract fun movieListFragment(): MovieListFragment

    @ContributesAndroidInjector(modules = arrayOf(SearchModule::class))
    abstract fun searchFragment(): SearchFragment

    @ContributesAndroidInjector()
    abstract fun movieDetailsFragment(): MovieDetailsFragment
}