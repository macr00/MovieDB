package com.moviedb.domain.pagination

import com.moviedb.data.model.MovieListResponseData

interface SearchPagination : Pagination<MovieListResponseData> {

    var query: String
}

class SearchPaginationImpl: MovieResultsPagination(), SearchPagination {

    override lateinit var query: String


}