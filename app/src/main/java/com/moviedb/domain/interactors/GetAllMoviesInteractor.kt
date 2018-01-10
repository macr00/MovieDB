package com.moviedb.domain.interactors

import com.moviedb.domain.pagination.GetAllMoviesPagination

data class GetAllMoviesInteractor(
        val pagination: GetAllMoviesPagination
): GetAllMoviesPagination by pagination