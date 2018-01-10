package com.moviedb.ui.list

import android.util.Log
import com.moviedb.data.model.MovieListResponseData
import com.moviedb.domain.GetMovieListInteractor
import com.moviedb.domain.usecase.UseCase
import com.moviedb.ui.base.BaseViewModel
import com.moviedb.ui.common.MovieListResponse
import com.moviedb.ui.common.MovieResultsPaginator
import io.reactivex.disposables.Disposable
import java.util.Date

class MovieListViewModel(
        private val getAllUseCase: UseCase<GetMovieListInteractor, MovieListResponseData>,
        private val paginator: MovieResultsPaginator
) : BaseViewModel() {

    private var lastResult: MovieListResponseData? = null

    fun getAllMovies() {
        getNextPage()?.let {
            disposables.add(getAllDisposable(GetMovieListInteractor(it,null)))
        }
    }

    fun getAllMovies(date: Date) {
        getNextPage()?.let {
            disposables.add(getAllDisposable(GetMovieListInteractor(it, date)))
        }
    }

    private fun getAllDisposable(interactor: GetMovieListInteractor): Disposable {
        return getAllUseCase.execute(interactor)
                .doOnSubscribe { displayLoading(true) }
                .doOnTerminate { displayLoading(false) }
                .subscribe({ onSuccess(it) }, { onError(it) })
    }

    private fun getNextPage(): Int? {
        lastResult?.let {
            return if (it.page != it.totalPages) it.page + 1 else null
        }
        return 1
    }

    private fun onSuccess(data: MovieListResponseData) {
        Log.d("List Result", data.toString())
        data.let {
            paginator.processResults(it)
            liveData.value = MovieListResponse(it)
        }
    }
}
