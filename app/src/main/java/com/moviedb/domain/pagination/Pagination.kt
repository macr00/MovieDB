package com.moviedb.domain.pagination

import com.moviedb.domain.interactors.Interactor

interface Pagination<T> : Interactor, Iterator {

    fun resetPagination()

    fun processResult(result: T)
}

interface Iterator {

    fun hasNext(): Boolean

    fun nextPage(): Int
}

