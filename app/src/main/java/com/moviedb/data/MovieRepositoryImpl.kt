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

    override fun getAll(nextPage: Int): Flowable<MovieListResponseData> {
        return api.discover(nextPage)
                .toFlowable()
                .subscribeOn(schedulers.io)
    }

    override fun search(nextPage: Int, title: String): Flowable<MovieListResponseData> {
        return api.search(nextPage, title)
                .toFlowable()
                .subscribeOn(schedulers.io)
    }

    override fun getMovie(id: Long): Flowable<MovieDetailData> {
        return api.getMovie(id)
                .toFlowable()
                .subscribeOn(schedulers.io)
    }
}