package com.moviedb.domain

import java.util.*


sealed class Interactor

data class GetMovieListInteractor(val date: Date?): Interactor()
data class SearchMoviesInteractor(val query: String): Interactor()
data class GetMovieInteractor(val id: Long): Interactor()
