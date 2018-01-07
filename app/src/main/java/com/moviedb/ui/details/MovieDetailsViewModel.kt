package com.moviedb.ui.details

import com.moviedb.domain.GetMovieInteractor
import com.moviedb.domain.usecase.GetMovieUseCase
import com.moviedb.ui.base.BaseViewModel


class MovieDetailsViewModel(
        private val useCase: GetMovieUseCase
): BaseViewModel(){

    fun getMovie(id: Long) {
        disposables.add(useCase.execute(GetMovieInteractor(id))
                .doOnSubscribe { /* TODO loading */ }
                .subscribe(
                        { /* TODO OnNext */ },
                        { /* TODO OnError */ }
                ))
    }
}