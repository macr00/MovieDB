package com.moviedb.app

import android.app.Application
import com.moviedb.domain.schedulers.RxAndroidSchedulers
import com.moviedb.domain.schedulers.RxSchedulers
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds abstract fun application(app: MovieDbApp): Application

    @Binds abstract fun schedulers(schedulers: RxAndroidSchedulers): RxSchedulers
}