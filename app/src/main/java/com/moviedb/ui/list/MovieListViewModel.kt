package com.moviedb.ui.list

import android.util.Log
import com.moviedb.data.model.MovieListResponseData
import com.moviedb.domain.GetMovieListInteractor
import com.moviedb.domain.SearchMoviesInteractor
import com.moviedb.domain.schedulers.RxSchedulers
import com.moviedb.domain.usecase.UseCase
import com.moviedb.ui.base.BaseViewModel
import io.reactivex.Flowable
import java.util.Date
import java.util.concurrent.TimeUnit

class MovieListViewModel(
        private val getAllUseCase: UseCase<GetMovieListInteractor, MovieListResponseData>,
        private val searchUseCase: UseCase<SearchMoviesInteractor, MovieListResponseData>,
        private val schedulers: RxSchedulers
) : BaseViewModel<MovieListResponseData>() {

    fun getAllMovies() {
        getAllMovies(GetMovieListInteractor(null))
    }

    fun getAllMovies(date: Date) {
        getAllMovies(GetMovieListInteractor(date))
    }

    private fun getAllMovies(interactor: GetMovieListInteractor) {
        disposables.add(getAllUseCase.execute(interactor)
                .subscribe(
                        { Log.d("List Result", it.toString()) },
                        { Log.d("List Result", it.message) }
                ))
    }

    fun searchMovies(query: Flowable<CharSequence>) {
        disposables.add(query
                .debounce(500, TimeUnit.MILLISECONDS, schedulers.ui)
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
