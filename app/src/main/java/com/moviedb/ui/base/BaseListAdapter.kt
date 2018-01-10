package com.moviedb.ui.base

import android.support.v7.widget.RecyclerView
import android.view.View

interface ListAdapter<T> {

    fun freshData(freshDataset: Collection<T>)

    fun addItems(newItems: Collection<T>)
}

abstract class BaseListAdapter<T, VH : BaseListAdapter.ViewHolder<T>>(
        protected var items: MutableList<T>,
        protected val listener: (T) -> Unit
) : RecyclerView.Adapter<VH>(), ListAdapter<T> {

    override fun getItemCount(): Int = items.size

    override fun freshData(freshDataset: Collection<T>) {
        this.items = freshDataset.toMutableList()
        notifyDataSetChanged()
    }

    override fun addItems(newItems: Collection<T>) {
        val start = items.size
        this.items.addAll(newItems)
        notifyItemRangeInserted(start, newItems.size)
    }

    abstract class ViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

        open fun bind(item: T, listener: (T) -> Unit) {
            with(itemView) {
                setOnClickListener { listener(item) }
            }
        }
    }
}