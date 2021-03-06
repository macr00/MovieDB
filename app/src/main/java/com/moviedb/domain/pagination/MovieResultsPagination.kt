package com.moviedb.domain.pagination

import com.moviedb.domain.model.MovieListResponseData

open class MovieResultsPagination : Pagination<MovieListResponseData> {

    private var isComplete: Boolean = false
    private var nextPage: Int = 1

    override fun hasNext(): Boolean {
        return !isComplete
    }

    override fun nextPage(): Int {
        return nextPage
    }

    override fun processResult(result: MovieListResponseData) {
        result.let {
            if (it.page < it.totalPages) {
                nextPage = it.page + 1
                isComplete = false
            } else {
                isComplete = true
            }
        }
    }

    override fun resetPagination() {
        nextPage = 1
        isComplete = false
    }

}