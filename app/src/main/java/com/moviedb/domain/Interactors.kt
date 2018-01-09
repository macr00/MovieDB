package com.moviedb.domain

import java.util.*


sealed class Interactor

data class GetMovieListInteractor(
        val nextPage: Int,
        val date: Date?
): Interactor()

data class SearchMoviesInteractor(
        val nextPage: Int,
        val query: String
): Interactor()

data class GetMovieInteractor(
        val id: Long
): Interactor()
