package com.moviedb.ui.common

import com.moviedb.data.model.MovieListResponseData
import javax.inject.Inject

class MovieResultsPaginator
@Inject constructor(): Paginator<MovieListResponseData> {

    override var nextPage: Int? = 1

    override fun processResults(results: MovieListResponseData) {
        results.let {
            nextPage = if (it.page < it.totalPages) it.page +1 else null
        }
    }
}