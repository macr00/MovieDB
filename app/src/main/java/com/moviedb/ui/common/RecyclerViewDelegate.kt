package com.moviedb.ui.common

import android.support.v7.widget.RecyclerView
import com.moviedb.ui.base.BaseListAdapter
import com.moviedb.ui.base.ListAdapter


class RecyclerViewDelegate<T>(
        val recyclerView: RecyclerView,
        val listAdapter: BaseListAdapter<T, *>,
        val lm: RecyclerView.LayoutManager
) : ListAdapter<T> by listAdapter {

    init {
        recyclerView.apply {
            adapter = listAdapter
            layoutManager = lm
        }
    }
}