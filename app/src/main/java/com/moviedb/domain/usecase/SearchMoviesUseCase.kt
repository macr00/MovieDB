package com.moviedb.domain.usecase

import com.moviedb.domain.model.MovieListResponseData
import com.moviedb.domain.MovieRepository
import com.moviedb.domain.interactors.SearchMoviesInteractor
import com.moviedb.domain.schedulers.RxSchedulers
import io.reactivex.Flowable
import javax.inject.Inject


class SearchMoviesUseCase
@Inject constructor(
        private val repository: MovieRepository,
        private val schedulers: RxSchedulers
): UseCase<SearchMoviesInteractor, MovieListResponseData> {

    override fun execute(interactor: SearchMoviesInteractor): Flowable<MovieListResponseData> {
        interactor.let {
            if (it.hasNext()) {
                return repository.search(interactor.nextPage(), it.year, it.query)
                        .doOnNext { interactor.processResult(it) }
                        .observeOn(schedulers.ui)
            }

            return Flowable.empty()
        }

    }
}