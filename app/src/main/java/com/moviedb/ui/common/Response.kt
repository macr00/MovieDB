package com.moviedb.ui.common

import com.moviedb.domain.model.MovieDetailData
import com.moviedb.domain.model.MovieListResponseData


sealed class Response
data class ErrorResponse(val throwable: Throwable): Response()
data class MovieListResponse(val data: MovieListResponseData) : Response()
data class MovieDetailsResponse(val data: MovieDetailData): Response()
