package com.moviedb.domain.schedulers

import io.reactivex.Scheduler

interface RxSchedulers {

    val ui: Scheduler

    val io: Scheduler

    val computation: Scheduler
}