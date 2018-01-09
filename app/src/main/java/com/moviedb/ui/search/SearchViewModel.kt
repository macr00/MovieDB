package com.moviedb.ui.search

import android.util.Log
import com.moviedb.data.model.MovieListResponseData
import com.moviedb.domain.SearchMoviesInteractor
import com.moviedb.domain.schedulers.RxSchedulers
import com.moviedb.domain.usecase.UseCase
import com.moviedb.ui.base.BaseViewModel
import com.moviedb.ui.common.MovieListResponse
import com.moviedb.ui.common.MovieResultsPaginator
import io.reactivex.Flowable
import org.reactivestreams.Publisher
import java.util.concurrent.TimeUnit


class SearchViewModel(
        private val searchUseCase: UseCase<SearchMoviesInteractor, MovieListResponseData>,
        private val paginator: MovieResultsPaginator,
        private val schedulers: RxSchedulers
) : BaseViewModel() {

    fun searchMovies(query: Flowable<CharSequence>) {
        disposables.add(query
                .debounce(500, TimeUnit.MILLISECONDS, schedulers.ui)
                .filter { it.length > 1 }
                .map { it.toString().trim { it <= ' ' } }
                .switchMap { searchMovieDisposable(SearchMoviesInteractor(1, it)) }
                .subscribe({ onSuccess(it)  }, { Log.d("Search Error", it.message) }))
    }

    private fun searchMovieDisposable(interactor: SearchMoviesInteractor): Publisher<MovieListResponseData> {
        return searchUseCase.execute(interactor)
                .doOnSubscribe { /* TODO */ }
                .onErrorResumeNext(Flowable.empty())
    }

    private fun onSuccess(data: MovieListResponseData) {
        Log.d("List Result", data.toString())
        data.let {
            paginator.processResults(it)
            liveData.value = MovieListResponse(it)
        }
    }
}