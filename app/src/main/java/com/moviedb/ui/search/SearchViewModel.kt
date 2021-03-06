package com.moviedb.ui.search

import android.util.Log
import com.moviedb.domain.interactors.SearchMoviesInteractor
import com.moviedb.domain.model.MovieListItemData
import com.moviedb.domain.model.MovieListResponseData
import com.moviedb.domain.usecase.UseCase
import com.moviedb.ui.base.BaseViewModel
import com.moviedb.ui.common.*
import io.reactivex.Flowable
import io.reactivex.Observable


class SearchViewModel(
        private val searchUseCase: UseCase<SearchMoviesInteractor, MovieListResponseData>,
        private val interactor: SearchMoviesInteractor,
        private val rxQuery: RxQuery
) : BaseViewModel(), NextPageScrollListener {

    private var results: MutableList<MovieListItemData>? = null
    private var isLoading: Boolean = false

    override fun loadNextPage() {
        if (!isLoading) {
            disposables.add(searchMovieFlowable(interactor).subscribe())
        }
    }

    fun setYear(year: Int?) {
        interactor.year = year
    }

    fun clearQuery() {
        interactor.query = ""
    }

    fun searchMovies(year: Int?) {
        if (!isLoading) {
            disposables.add(searchMovieFlowable(
                    interactor.apply {
                        resetPagination()
                        this.year = year
                    }
            ).subscribe())
        }
    }

    fun searchMovies(queryChars: Observable<CharSequence>) {
        disposables.add(rxQuery.asFlowable(queryChars)
                .map {
                    interactor.apply {
                        resetPagination()
                        query = it
                    }
                }
                .switchMap { searchMovieFlowable(it) }
                .subscribe())
    }

    internal fun searchMovieFlowable(interactor: SearchMoviesInteractor): Flowable<MovieListResponseData> {
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
            if (it.page == 1) {
                results = it.results.toMutableList()
                results?.let { liveData.value = FreshSearchResponse(it) }
            } else {
                results?.addAll(it.results)
                results?.let { liveData.value = NextPageSearchResponse(it) }
            }
        }
    }

    override fun onLoading(isLoading: Boolean) {
        super.onLoading(isLoading)
        this.isLoading = isLoading
    }
}