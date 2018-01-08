package com.moviedb.ui.list

import com.moviedb.data.model.MovieListResponseData
import com.moviedb.domain.GetMovieListInteractor
import com.moviedb.domain.SearchMoviesInteractor
import com.moviedb.domain.schedulers.RxSchedulers
import com.moviedb.domain.usecase.UseCase
import com.moviedb.ui.base.BaseViewModelFactory
import javax.inject.Inject

class MovieListViewModelFactory
@Inject constructor(
        private val getAllUseCase: UseCase<GetMovieListInteractor, MovieListResponseData>,
        private val searchUseCase: UseCase<SearchMoviesInteractor, MovieListResponseData>,
        private val schedulers: RxSchedulers
) : BaseViewModelFactory<MovieListViewModel>() {

    override val viewModel: MovieListViewModel by lazy {
        MovieListViewModel(getAllUseCase, searchUseCase, schedulers)
    }

    override val vmClass: Class<MovieListViewModel> = MovieListViewModel::class.java
}