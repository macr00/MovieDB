package com.moviedb

import com.moviedb.domain.schedulers.RxSchedulers
import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler
import io.reactivex.schedulers.Schedulers


class RxSchedulersTest: RxSchedulers {
    override val ui: Scheduler = TestScheduler()
    override val io: Scheduler = TestScheduler()
    override val computation: Scheduler = TestScheduler()
}

class RxSchedulersTrampoline: RxSchedulers {
    override val ui: Scheduler = Schedulers.trampoline()
    override val io: Scheduler = Schedulers.trampoline()
    override val computation: Scheduler = Schedulers.trampoline()
}
