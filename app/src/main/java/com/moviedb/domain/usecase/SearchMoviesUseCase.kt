package com.moviedb.domain.usecase

import com.moviedb.data.model.MovieListResponseData
import com.moviedb.domain.MovieRepository
import com.moviedb.domain.SearchMoviesInteractor
import com.moviedb.domain.schedulers.RxSchedulers
import io.reactivex.Flowable
import javax.inject.Inject


class SearchMoviesUseCase
@Inject constructor(
        private val repository: MovieRepository,
        private val schedulers: RxSchedulers
): UseCase<SearchMoviesInteractor, MovieListResponseData> {

    override fun execute(interactor: SearchMoviesInteractor): Flowable<MovieListResponseData> {
        return repository.search(interactor.query).observeOn(schedulers.ui)
    }
}