package com.moviedb.ui.common

interface Paginator<T> {

    var nextPage: Int?

    fun processResults(results: T)
}

