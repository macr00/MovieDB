package com.moviedb.ui.list

import com.moviedb.data.model.MovieListResponseData
import com.moviedb.domain.interactors.GetAllMoviesInteractor
import com.moviedb.domain.pagination.GetAllMoviesPagination
import com.moviedb.domain.usecase.UseCase
import com.moviedb.ui.base.BaseViewModelFactory
import com.moviedb.domain.pagination.MovieResultsPagination
import javax.inject.Inject

class MovieListViewModelFactory
@Inject constructor(
        private val getAllUseCase: UseCase<GetAllMoviesInteractor, MovieListResponseData>,
        private val interactor: GetAllMoviesInteractor
) : BaseViewModelFactory<MovieListViewModel>() {

    override val viewModel: MovieListViewModel by lazy {
        MovieListViewModel(getAllUseCase, interactor)
    }

    override val vmClass: Class<MovieListViewModel> = MovieListViewModel::class.java
}