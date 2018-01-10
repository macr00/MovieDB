package com.moviedb.domain.usecase

import com.moviedb.data.model.MovieListResponseData
import com.moviedb.domain.MovieRepository
import com.moviedb.domain.interactors.GetAllMoviesInteractor
import com.moviedb.domain.schedulers.RxSchedulers
import io.reactivex.Flowable
import javax.inject.Inject


class GetMovieListUseCase
@Inject constructor(
        private val repository: MovieRepository,
        private val schedulers: RxSchedulers
) : UseCase<GetAllMoviesInteractor, MovieListResponseData> {

    override fun execute(interactor: GetAllMoviesInteractor): Flowable<MovieListResponseData> {
        if (interactor.hasNext()) {
            return repository.getAll(interactor.nextPage())
                    .doOnNext { interactor.processResult(it) }
                    .observeOn(schedulers.ui)
        }

        return Flowable.empty()
    }
}

