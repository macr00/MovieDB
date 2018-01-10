package com.moviedb.ui.list

import android.util.Log
import com.moviedb.data.model.MovieListResponseData
import com.moviedb.domain.interactors.GetAllMoviesInteractor
import com.moviedb.domain.usecase.UseCase
import com.moviedb.ui.base.BaseViewModel
import com.moviedb.ui.common.NextPageScrollListener
import com.moviedb.ui.common.MovieListResponse
import io.reactivex.disposables.Disposable
import java.util.Date

class MovieListViewModel(
        private val getAllUseCase: UseCase<GetAllMoviesInteractor, MovieListResponseData>,
        private val interactor: GetAllMoviesInteractor
) : BaseViewModel(), NextPageScrollListener {

    private var isLoading: Boolean = false

    override fun loadNextPage() {
        if(!isLoading) {
            disposables.add(getAllDisposable(interactor))
        }
    }

    fun getAllMovies() {
        disposables.add(getAllDisposable(interactor))
    }

    fun getAllMovies(date: Date) {
        disposables.add(getAllDisposable(interactor))
    }

    private fun getAllDisposable(interactor: GetAllMoviesInteractor): Disposable {
        return getAllUseCase.execute(interactor)
                .doOnSubscribe { onLoading(true) }
                .doOnTerminate { onLoading(false) }
                .subscribe({ onSuccess(it) }, { onError(it) })
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
