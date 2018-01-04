package com.moviedb.data

data class MovieListResponseObject(
      val page: Int,
      val totalResults: Int,
      val totalPages: Int,
      val results: List<MovieListResultObject>
)