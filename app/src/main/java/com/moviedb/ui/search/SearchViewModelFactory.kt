package com.moviedb.ui.search

import com.moviedb.data.model.MovieListResponseData
import com.moviedb.domain.SearchMoviesInteractor
import com.moviedb.domain.schedulers.RxSchedulers
import com.moviedb.domain.usecase.UseCase
import com.moviedb.ui.base.BaseViewModelFactory
import com.moviedb.ui.common.MovieResultsPaginator
import javax.inject.Inject


class SearchViewModelFactory
@Inject constructor(
        private val searchUseCase: UseCase<SearchMoviesInteractor, MovieListResponseData>,
        private val paginator: MovieResultsPaginator,
        private val schedulers: RxSchedulers
) : BaseViewModelFactory<SearchViewModel>() {

    override val viewModel: SearchViewModel by lazy {
        SearchViewModel(searchUseCase, paginator, schedulers)
    }

    override val vmClass: Class<SearchViewModel> = SearchViewModel::class.java
}