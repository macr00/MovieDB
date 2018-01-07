package com.moviedb.domain.usecase

import com.moviedb.data.model.MovieListResponseData
import com.moviedb.domain.GetMovieListInteractor
import com.moviedb.domain.MovieRepository
import com.moviedb.domain.schedulers.RxSchedulers
import io.reactivex.Flowable


class GetMovieListUseCase(
        private val repository: MovieRepository,
        private val schedulers: RxSchedulers
): UseCase<GetMovieListInteractor, MovieListResponseData> {

    override fun execute(interactor: GetMovieListInteractor): Flowable<MovieListResponseData> {
        return repository.getAll().observeOn(schedulers.ui)
    }
}