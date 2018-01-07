package com.moviedb.domain.schedulers

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RxAndroidSchedulers : RxSchedulers {

    override val uiScheduler: Scheduler
        get() = AndroidSchedulers.mainThread()
    override val ioScheduler: Scheduler
        get() = Schedulers.io()
    override val computationScheduler: Scheduler
        get() = Schedulers.computation()
}