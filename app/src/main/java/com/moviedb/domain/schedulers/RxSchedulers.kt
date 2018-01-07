package com.moviedb.domain.schedulers

import io.reactivex.Scheduler

interface RxSchedulers {

    val uiScheduler: Scheduler

    val ioScheduler: Scheduler

    val computationScheduler: Scheduler
}