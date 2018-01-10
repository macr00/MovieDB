package com.moviedb.domain.interactors

import com.moviedb.domain.pagination.SearchPagination

data class SearchMoviesInteractor(
        val pagination: SearchPagination
): SearchPagination by pagination