package com.moviedb.domain.schedulers

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RxAndroidSchedulers
@Inject constructor() : RxSchedulers {

    override val ui: Scheduler
        get() = AndroidSchedulers.mainThread()
    override val io: Scheduler
        get() = Schedulers.io()
    override val computation: Scheduler
        get() = Schedulers.computation()
}