package com.moviedb.data.model

class MovieDetailData(
        val id: Long,
        val releaseDate: String,
        val title: String,
        val overview: String,
        val originalLanguage: String,
        val posterPath: String,
        val backdropPath: String,
        val genreIds: List<GenreObject>,
        val voteCount: Int,
        val voteAverage: Double,
        val adult: Boolean
)