package com.moviedb.domain.model

data class MovieListItemData(
        val id: Long,
        val releaseDate: String,
        val title: String,
        val overview: String,
        val originalLanguage: String,
        val posterPath: String,
        val backdropPath: String,
        val genreIds: List<Int>,
        val voteCount: Int,
        val voteAverage: Double,
        val adult: Boolean
)