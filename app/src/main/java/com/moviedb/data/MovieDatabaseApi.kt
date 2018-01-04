package com.moviedb.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

interface MovieDatabaseApi {

    @GET("discover/movie")
    fun discover(): Observable<MovieListResultObject>

    @GET("search/movie")
    fun search(@Query("query") query: String): Observable<MovieListResultObject>

    @GET("movie/{id}")
    fun getMovie(@Path("id") id: String): Observable<MovieDetailResultObject>
}