package com.moviedb.ui.common

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class InfiniteScrollListener(
        private val listener: () -> Unit
): RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val totalCount = recyclerView.layoutManager.itemCount
        val last = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

        if (totalCount <= (last + THRESHOLD)) {
            listener.invoke()
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
    }

    companion object {
        const val THRESHOLD = 5
    }
}