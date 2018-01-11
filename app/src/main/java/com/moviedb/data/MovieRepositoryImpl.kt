package com.moviedb.data

import com.moviedb.data.model.MovieDetailData
import com.moviedb.data.model.MovieListResponseData
import com.moviedb.domain.MovieRepository
import com.moviedb.domain.schedulers.RxSchedulers
import io.reactivex.Flowable
import javax.inject.Inject

class MovieRepositoryImpl
@Inject constructor(
        private val api: MovieDatabaseApi,
        private val schedulers: RxSchedulers
) : MovieRepository {

    override fun getAll(nextPage: Int, year: Int?): Flowable<MovieListResponseData> {
        return api.discover(nextPage, year)
                .toFlowable()
                .subscribeOn(schedulers.io)
    }

    override fun search(nextPage: Int,  year: Int?, title: String): Flowable<MovieListResponseData> {
        return api.search(nextPage, year, title)
                .toFlowable()
                .subscribeOn(schedulers.io)
    }

    override fun getMovie(id: Long): Flowable<MovieDetailData> {
        return api.getMovie(id)
                .toFlowable()
                .subscribeOn(schedulers.io)
    }
}