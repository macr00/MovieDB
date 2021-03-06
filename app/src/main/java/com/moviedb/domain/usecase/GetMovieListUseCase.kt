package com.moviedb.domain.usecase

import com.moviedb.domain.model.MovieListResponseData
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
        interactor.let {
            if (it.hasNext()) {
                return repository.getAll(it.nextPage(), it.year)
                        .doOnNext { interactor.processResult(it) }
                        .observeOn(schedulers.ui)
            }

            return Flowable.empty()
        }
    }
}

