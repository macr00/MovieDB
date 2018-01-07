package com.moviedb.ui.list

import com.moviedb.domain.GetMovieListInteractor
import com.moviedb.domain.SearchMoviesInteractor
import com.moviedb.domain.schedulers.RxSchedulers
import com.moviedb.domain.usecase.GetMovieListUseCase
import com.moviedb.domain.usecase.SearchMoviesUseCase
import com.moviedb.ui.base.BaseViewModel
import io.reactivex.processors.FlowableProcessor
import java.util.Date
import java.util.concurrent.TimeUnit

class MovieListViewModel(
        private val getAllUseCase: GetMovieListUseCase,
        private val searchUseCase: SearchMoviesUseCase,
        private val schedulers: RxSchedulers
): BaseViewModel() {

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

    fun searchMovies(query: FlowableProcessor<String>) {
        disposables.add(query
                .filter { it.isNotEmpty() && it.length > 1 }
                .debounce(500, TimeUnit.MILLISECONDS, schedulers.uiScheduler)
                .observeOn(schedulers.uiScheduler)
                .map { SearchMoviesInteractor(it) }
                .flatMap { searchUseCase.execute(it) }
                .subscribe(
                        {},
                        {}
                ))
    }

}