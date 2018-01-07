package com.moviedb.data

import com.moviedb.data.model.MovieDetailData
import com.moviedb.data.model.MovieListResponseData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDatabaseApi {

    @GET("discover/movie")
    fun discover(): Single<MovieListResponseData>

    @GET("search/movie")
    fun search(@Query("query") query: String): Single<MovieListResponseData>

    @GET("movie/{id}")
    fun getMovie(@Path("id") id: Long): Single<MovieDetailData>
}