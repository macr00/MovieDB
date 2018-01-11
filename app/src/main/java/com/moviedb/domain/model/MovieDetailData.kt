package com.moviedb.domain.model

class MovieDetailData(
        val id: Long,
        val releaseDate: String,
        val title: String,
        val tagline: String,
        val overview: String,
        val posterPath: String,
        val backdropPath: String,
        val genres: List<GenreData>,
        val voteCount: Int,
        val voteAverage: Double,
        val adult: Boolean
)