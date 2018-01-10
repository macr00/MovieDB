package com.moviedb.ui.search

import com.moviedb.data.model.MovieListResponseData
import com.moviedb.domain.interactors.SearchMoviesInteractor
import com.moviedb.domain.schedulers.RxSchedulers
import com.moviedb.domain.usecase.UseCase
import com.moviedb.ui.base.BaseViewModelFactory
import com.moviedb.domain.pagination.MovieResultsPagination
import com.moviedb.domain.pagination.SearchPagination
import javax.inject.Inject


class SearchViewModelFactory
@Inject constructor(
        private val searchUseCase: UseCase<SearchMoviesInteractor, MovieListResponseData>,
        private val interactor: SearchMoviesInteractor,
        private val schedulers: RxSchedulers
) : BaseViewModelFactory<SearchViewModel>() {

    override val viewModel: SearchViewModel by lazy {
        SearchViewModel(searchUseCase, interactor, schedulers)
    }

    override val vmClass: Class<SearchViewModel> = SearchViewModel::class.java
}