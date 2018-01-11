package com.moviedb.domain.model

data class MovieListResponseData(
      val page: Int,
      val totalResults: Int,
      val totalPages: Int,
      val results: List<MovieListItemData>
)