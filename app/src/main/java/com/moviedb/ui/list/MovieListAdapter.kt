package com.moviedb.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.moviedb.R
import com.moviedb.data.model.MovieListItemData
import com.moviedb.ui.base.BaseListAdapter
import kotlinx.android.synthetic.main.movie_list_item.view.*

class MovieListAdapter(
        items: MutableList<MovieListItemData>,
        listener: (MovieListItemData) -> Unit
) : BaseListAdapter<MovieListItemData, MovieListAdapter.ViewHolder>(items,listener) {

    init {
        setHasStableIds(true)
    }

    override fun onBindViewHolder(holder: MovieListAdapter.ViewHolder?, position: Int) {
        holder?.bind(items[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_list_item, parent, false)
        return MovieListAdapter.ViewHolder(view)
    }

    override fun getItemId(position: Int) = items[position].id

    class ViewHolder(itemView: View) : BaseListAdapter.ViewHolder<MovieListItemData>(itemView) {
        override fun bind(item: MovieListItemData, listener: (MovieListItemData) -> Unit) {
            super.bind(item, listener)
            with(itemView) {
                movie_list_item_title.text = item.title
            }
        }
    }
}
