package com.moviedb.domain.usecase

import com.moviedb.domain.Interactor
import io.reactivex.Flowable

interface UseCase<I: Interactor, T> {

    fun execute(interactor: I): Flowable<T>
}

