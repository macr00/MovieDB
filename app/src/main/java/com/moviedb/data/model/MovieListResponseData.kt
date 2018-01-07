package com.moviedb.data.model

data class MovieListResponseData(
      val page: Int,
      val totalResults: Int,
      val totalPages: Int,
      val results: List<MovieListItemData>
)