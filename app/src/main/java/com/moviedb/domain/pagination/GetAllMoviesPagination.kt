package com.moviedb.domain.pagination

import com.moviedb.data.model.MovieListResponseData
import java.util.*

interface GetAllMoviesPagination : Pagination<MovieListResponseData> {

    var date: Date?
}

class GetAllMoviesPaginationImpl : MovieResultsPagination(), GetAllMoviesPagination {

    override var date: Date? = null
}