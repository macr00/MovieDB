package com.moviedb

import com.moviedb.domain.schedulers.RxSchedulers
import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler
import io.reactivex.schedulers.Schedulers


class RxSchedulersTest(tester: TestScheduler) : RxSchedulers {
    override val ui: Scheduler = tester
    override val io: Scheduler = tester
    override val computation: Scheduler = tester
}

class RxSchedulersTrampoline : RxSchedulers {
    override val ui: Scheduler = Schedulers.trampoline()
    override val io: Scheduler = Schedulers.trampoline()
    override val computation: Scheduler = Schedulers.trampoline()
}
