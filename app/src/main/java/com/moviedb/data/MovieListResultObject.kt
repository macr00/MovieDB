package com.moviedb.data

data class MovieListResultObject(
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