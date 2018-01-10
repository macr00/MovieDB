package com.moviedb.domain.pagination

import com.moviedb.data.model.MovieListResponseData

interface SearchPagination : Pagination<MovieListResponseData> {

    var query: String

    fun reset()
}

class SearchPaginationImpl: MovieResultsPagination(), SearchPagination {

    override lateinit var query: String

    override fun reset() {
        nextPage = 1
        isComplete = false
    }
}