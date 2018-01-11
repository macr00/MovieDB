package com.moviedb.domain.interactors

import com.moviedb.domain.pagination.SearchPagination

data class SearchMoviesInteractor(
        val pagination: SearchPagination,
        override var year: Int?
): MovieListInteractor, SearchPagination by pagination