package com.moviedb.ui.common

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.moviedb.ui.base.BaseListAdapter
import com.moviedb.ui.base.ListAdapter


class RecyclerViewDelegate<T>(
        val recyclerView: RecyclerView,
        val listAdapter: BaseListAdapter<T, *>,
        val llm: LinearLayoutManager
) : ListAdapter<T> by listAdapter {

    init {
        recyclerView.apply {
            adapter = listAdapter
            layoutManager = llm
        }
    }
}