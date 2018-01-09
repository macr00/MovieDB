package com.moviedb.ui.list

import android.util.Log
import com.moviedb.data.model.MovieListResponseData
import com.moviedb.domain.GetMovieListInteractor
import com.moviedb.domain.SearchMoviesInteractor
import com.moviedb.domain.schedulers.RxSchedulers
import com.moviedb.domain.usecase.UseCase
import com.moviedb.ui.base.BaseViewModel
import com.moviedb.ui.common.MovieListResponse
import io.reactivex.Flowable
import java.util.Date
import java.util.concurrent.TimeUnit

class MovieListViewModel(
        private val getAllUseCase: UseCase<GetMovieListInteractor, MovieListResponseData>,
        private val searchUseCase: UseCase<SearchMoviesInteractor, MovieListResponseData>,
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
                .doOnSubscribe { onLoading() }
                .subscribe({ onSuccess(it) }, { onError(it) }))
    }

    fun searchMovies(query: Flowable<CharSequence>) {
        disposables.add(query
                .debounce(500, TimeUnit.MILLISECONDS, schedulers.ui)
                .filter { it.length > 1 }
                .map { it.toString().trim { it <= ' ' } }
                .switchMap {
                    searchUseCase.execute(SearchMoviesInteractor(it))
                            .doOnSubscribe { onLoading() }
                            .onErrorResumeNext(Flowable.empty())
                }
                .subscribe({ onSuccess(it) }, { onError(it) }))
    }

    private fun onLoading() {

    }

    private fun onSuccess(data: MovieListResponseData) {
        Log.d("List Result", data.toString())
        liveData.value = MovieListResponse(data)
    }

    private fun onError(throwable: Throwable) {
        Log.d("List Error", throwable.toString())
    }

}
