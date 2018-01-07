package com.moviedb.ui.list

import com.moviedb.domain.GetMovieListInteractor
import com.moviedb.domain.SearchMoviesInteractor
import com.moviedb.domain.schedulers.RxSchedulers
import com.moviedb.domain.usecase.GetMovieListUseCase
import com.moviedb.domain.usecase.SearchMoviesUseCase
import com.moviedb.ui.base.BaseViewModel
import io.reactivex.Flowable
import java.util.Date
import java.util.concurrent.TimeUnit

class MovieListViewModel(
        private val getAllUseCase: GetMovieListUseCase,
        private val searchUseCase: SearchMoviesUseCase,
        private val schedulers: RxSchedulers
) : BaseViewModel() {

    fun getAllMovies() {
        getAllMovies(GetMovieListInteractor(null))
    }

    fun getAllMovies(date: Date) {
        getAllMovies(GetMovieListInteractor(date))
    }

    private fun getAllMovies(interactor: GetMovieListInteractor) {
        disposables.add(getAllUseCase.execute(interactor)
                .subscribe(
                        {},
                        {}
                ))
    }

    fun searchMovies(query: Flowable<CharSequence>) {
        disposables.add(query
                .debounce(500, TimeUnit.MILLISECONDS, schedulers.uiScheduler)
                .filter { it.length > 1 }
                .map { it.toString().trim { it <= ' ' } }
                .switchMap {
                    searchUseCase.execute(SearchMoviesInteractor(it))
                            .onErrorResumeNext(Flowable.empty())
                }
                .subscribe(
                        {},
                        {}
                ))
    }

}