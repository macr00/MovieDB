package com.moviedb.domain.interactors

import com.moviedb.domain.model.MovieListResponseData
import com.moviedb.domain.pagination.Pagination

data class GetAllMoviesInteractor(
        val pagination: Pagination<MovieListResponseData>,
        override var year: Int?
): MovieListInteractor, Pagination<MovieListResponseData> by pagination
