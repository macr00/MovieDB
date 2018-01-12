package com.moviedb.ui.search

import com.moviedb.domain.schedulers.RxSchedulers
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import java.util.concurrent.TimeUnit


interface RxQuery {

    fun asFlowable(chars: Observable<CharSequence>): Flowable<String>

}

class RxQueryImpl(val schedulers: RxSchedulers) : RxQuery {

    override fun asFlowable(chars: Observable<CharSequence>) : Flowable<String> {
        return chars.debounce(500, TimeUnit.MILLISECONDS, schedulers.computation)
                .map { it.toString().trim { it <= ' ' } }
                .filter { it.length > 1 }
                .toFlowable(BackpressureStrategy.LATEST)
    }
}