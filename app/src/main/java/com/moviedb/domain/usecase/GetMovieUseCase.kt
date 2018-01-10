package com.moviedb.domain.usecase

import com.moviedb.data.model.MovieDetailData
import com.moviedb.domain.interactors.GetMovieInteractor
import com.moviedb.domain.MovieRepository
import com.moviedb.domain.schedulers.RxSchedulers
import io.reactivex.Flowable
import javax.inject.Inject

class GetMovieUseCase
@Inject constructor(
        private val repository: MovieRepository,
        private val schedulers: RxSchedulers
): UseCase<GetMovieInteractor, MovieDetailData> {

    override fun execute(interactor: GetMovieInteractor): Flowable<MovieDetailData> {
        return repository.getMovie(interactor.id)
                .observeOn(schedulers.ui)
    }
}

