package com.moviedb.ui.common

import com.moviedb.domain.model.MovieDetailData
import com.moviedb.domain.model.MovieListItemData


sealed class Response
data class ErrorResponse(val throwable: Throwable): Response()
data class FreshMovieListResponse(val data: MutableList<MovieListItemData>) : Response()
data class NextPageMovieListResponse(val data: MutableList<MovieListItemData>) : Response()
data class FreshSearchResponse(val data: MutableList<MovieListItemData>) : Response()
data class NextPageSearchResponse(val data: MutableList<MovieListItemData>) : Response()
data class MovieDetailsResponse(val data: MovieDetailData): Response()
