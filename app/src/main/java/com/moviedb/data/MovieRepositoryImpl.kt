package com.moviedb.data

import com.moviedb.data.model.MovieDetailData
import com.moviedb.data.model.MovieListResponseData
import com.moviedb.domain.MovieRepository
import com.moviedb.domain.schedulers.RxSchedulers
import io.reactivex.Flowable

class MovieRepositoryImpl(
        private val api: MovieDatabaseApi,
        private val schedulers: RxSchedulers
): MovieRepository {

    override fun getAll(): Flowable<MovieListResponseData> {
        return api.discover()
                .toFlowable()
                .subscribeOn(schedulers.ioScheduler)
    }

    override fun search(title: String): Flowable<MovieListResponseData> {
        return api.search(title)
                .toFlowable()
                .subscribeOn(schedulers.ioScheduler)
    }

    override fun getMovie(id: Long): Flowable<MovieDetailData> {
        return api.getMovie(id)
                .toFlowable()
                .subscribeOn(schedulers.ioScheduler)
    }
}