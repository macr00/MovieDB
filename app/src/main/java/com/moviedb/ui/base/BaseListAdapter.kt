package com.moviedb.ui.base

import android.support.v7.widget.RecyclerView
import android.view.View

interface ListAdapter<T> {

    fun freshData(items: Collection<T>)

    fun addItems(items: Collection<T>)
}

abstract class BaseListAdapter<T, VH : BaseListAdapter.ViewHolder<T>>(
        protected var items: MutableList<T>,
        protected val listener: (T) -> Unit
) : RecyclerView.Adapter<VH>(), ListAdapter<T> {

    override fun getItemCount(): Int = items.size

    override fun freshData(items: Collection<T>) {
        this.items = items.toMutableList()
        notifyDataSetChanged()
    }

    override fun addItems(items: Collection<T>) {
        val start = items.size
        this.items.addAll(items)
        notifyItemRangeInserted(start, items.size)
    }

    abstract class ViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

        open fun bind(item: T, listener: (T) -> Unit) {
            with(itemView) {
                setOnClickListener { listener(item) }
            }
        }
    }
}