package com.moviedb.domain

import com.moviedb.data.model.MovieDetailData
import com.moviedb.data.model.MovieListResponseData
import io.reactivex.Flowable

interface MovieRepository {

    fun getAll(): Flowable<MovieListResponseData>

    fun search(title: String): Flowable<MovieListResponseData>

    fun getMovie(id: Long): Flowable<MovieDetailData>
}