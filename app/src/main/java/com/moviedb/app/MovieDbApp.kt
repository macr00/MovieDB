package com.moviedb.app


import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class MovieDbApp: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
                .application(this)
                .build()
    }
}