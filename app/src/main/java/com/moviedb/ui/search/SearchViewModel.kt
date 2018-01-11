package com.moviedb.ui.search

import android.util.Log
import com.moviedb.data.model.MovieListResponseData
import com.moviedb.domain.interactors.SearchMoviesInteractor
import com.moviedb.domain.schedulers.RxSchedulers
import com.moviedb.domain.usecase.UseCase
import com.moviedb.ui.base.BaseViewModel
import com.moviedb.ui.common.NextPageScrollListener
import com.moviedb.ui.common.MovieListResponse
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import org.reactivestreams.Publisher
import java.util.concurrent.TimeUnit


class SearchViewModel(
        private val searchUseCase: UseCase<SearchMoviesInteractor, MovieListResponseData>,
        private val interactor: SearchMoviesInteractor,
        private val schedulers: RxSchedulers
) : BaseViewModel(), NextPageScrollListener {

    private var isLoading: Boolean = false

    override fun loadNextPage() {
        if (!isLoading) {
            disposables.add(searchMovieDisposable(interactor).subscribe())
        }
    }

    fun searchMovies(year: Int?) {
        if (!isLoading) {
            disposables.add(searchMovieDisposable(interactor
                    .apply {
                        reset()
                        this.year = year
                    }
            ).subscribe())
        }
    }

    fun searchMovies(queryChars: Flowable<CharSequence>) {
        disposables.add(queryChars
                .debounce(500, TimeUnit.MILLISECONDS, schedulers.computation)
                .filter { it.length > 1 }
                .map { chars ->
                    interactor.apply {
                        reset()
                        query = chars.toString().trim { it <= ' ' }
                    }
                }
                .switchMap { searchMovieDisposable(it) }
                .subscribe())
    }

    private fun searchMovieDisposable(interactor: SearchMoviesInteractor): Flowable<MovieListResponseData> {
        return searchUseCase.execute(interactor)
                .doOnSubscribe { onLoading(true) }
                .doOnTerminate { onLoading(false) }
                .doOnError { onError(it) }
                .doOnNext { onSuccess(it) }
                .onErrorResumeNext(Flowable.empty())
    }

    private fun onSuccess(data: MovieListResponseData) {
        Log.d("List Result", data.toString())
        data.let {
            liveData.value = MovieListResponse(it)
        }
    }

    override fun onLoading(isLoading: Boolean) {
        super.onLoading(isLoading)
        this.isLoading = isLoading
    }
}