package com.moviedb.ui.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.moviedb.R
import com.moviedb.data.model.MovieListItemData
import kotlinx.android.synthetic.main.movie_list_item.view.*
import javax.inject.Inject

class MovieListAdapter
@Inject constructor() : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    private var items = mutableListOf<MovieListItemData>()

    override fun onBindViewHolder(holder: MovieListAdapter.ViewHolder?, position: Int) {
        holder?.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_list_item, parent, false)
        return MovieListAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun getItemId(position: Int): Long {
        return items[position].id
    }

    fun addItems(list: List<MovieListItemData>) {
        val start = items.size
        items.addAll(list)
        notifyItemRangeInserted(start, list.size)
    }

    fun newList(list: List<MovieListItemData>) {
        items = list.toMutableList()
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title: TextView = with(view) { movie_list_item_title }

        fun bind(item: MovieListItemData) {
            title.text = item.title
        }
    }
}
