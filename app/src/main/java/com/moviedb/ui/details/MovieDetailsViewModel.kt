package com.moviedb.ui.details

import com.moviedb.data.model.MovieDetailData
import com.moviedb.domain.interactors.GetMovieInteractor
import com.moviedb.domain.usecase.UseCase
import com.moviedb.ui.base.BaseViewModel
import com.moviedb.ui.common.ErrorResponse
import com.moviedb.ui.common.MovieDetailsResponse


class MovieDetailsViewModel(
        private val useCase: UseCase<GetMovieInteractor, MovieDetailData>
): BaseViewModel(){

    fun getMovie(id: Long) {
        disposables.add(useCase.execute(GetMovieInteractor(id))
                .doOnSubscribe { onLoading(true) }
                .doAfterTerminate { onLoading(false) }
                .subscribe(
                        { liveData.value = MovieDetailsResponse(it) },
                        { liveData.value = ErrorResponse(it) }
                ))
    }
}