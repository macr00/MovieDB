package com.moviedb.domain.usecase

import com.moviedb.domain.Interactor
import io.reactivex.Flowable

interface UseCase<in In: Interactor, T> {

    fun execute(interactor: In): Flowable<T>
}

