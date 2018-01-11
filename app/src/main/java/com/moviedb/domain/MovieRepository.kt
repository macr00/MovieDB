package com.moviedb.domain

import com.moviedb.data.model.MovieDetailData
import com.moviedb.data.model.MovieListResponseData
import io.reactivex.Flowable

interface MovieRepository {

    fun getAll(nextPage: Int, year: Int?): Flowable<MovieListResponseData>

    fun search(nextPage: Int, year: Int?, title: String): Flowable<MovieListResponseData>

    fun getMovie(id: Long): Flowable<MovieDetailData>
}