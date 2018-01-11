package com.moviedb.domain.interactors

import com.moviedb.domain.model.MovieListResponseData
import com.moviedb.domain.pagination.Pagination

interface GetAllMoviesInteractor: MovieListInteractor, Pagination<MovieListResponseData>

data class GetAllMoviesInteractorImpl(
        val pagination: Pagination<MovieListResponseData>,
        override var year: Int?
): GetAllMoviesInteractor, Pagination<MovieListResponseData> by pagination
