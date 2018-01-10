package com.moviedb.domain.usecase

import com.moviedb.domain.interactors.Interactor
import io.reactivex.Flowable

interface UseCase<I: Interactor, T> {

    fun execute(interactor: I): Flowable<T>
}

