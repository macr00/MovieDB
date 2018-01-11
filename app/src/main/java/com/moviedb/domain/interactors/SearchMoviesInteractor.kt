package com.moviedb.domain.interactors

import com.moviedb.domain.pagination.SearchPagination

interface SearchMoviesInteractor: MovieListInteractor, SearchPagination

data class SearchMoviesInteractorData(
        val pagination: SearchPagination,
        override var year: Int?
): SearchMoviesInteractor, SearchPagination by pagination