package com.moviedb.ui.search


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.moviedb.R
import com.moviedb.domain.model.MovieListItemData
import com.moviedb.ui.base.BaseFragment
import com.moviedb.ui.common.*
import com.moviedb.ui.details.MovieDetailsActivity
import com.moviedb.ui.list.MovieListAdapter
import com.moviedb.ui.list.MovieListFragment
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

class SearchFragment : BaseFragment() {

    @Inject
    lateinit var searchViewModelFactory: SearchViewModelFactory
    lateinit private var searchViewModel: SearchViewModel
    lateinit private var recyclerViewDelegate: RecyclerViewDelegate<MovieListItemData>

    private var recreatedFromSavedState: Boolean = false

    override val fragment: BaseFragment = this

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recreatedFromSavedState = (savedInstanceState != null)
        recyclerViewDelegate = RecyclerViewDelegate(
                recyclerView = search_results_rv,
                llm = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false),
                listAdapter = MovieListAdapter(mutableListOf(), {
                    startActivity(MovieDetailsActivity.createIntent(activity, it.id, it.title))
                })
        )
        searchViewModel = ViewModelProviders
                .of(activity, searchViewModelFactory)
                .get(SearchViewModel::class.java)
                .apply {
                    response.observe(this@SearchFragment, Observer { onLiveDataUpdated(it) })
                }
        search_results_rv.addOnScrollListener(InfiniteScrollListener( { searchViewModel.loadNextPage() }))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(MovieListFragment.LIST_STATE, recyclerViewDelegate.llm.onSaveInstanceState())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.getParcelable<Parcelable>(MovieListFragment.LIST_STATE)
                .let { recyclerViewDelegate.llm.onRestoreInstanceState(it) }
    }

    override fun onLiveDataUpdated(response: Response?) {
        response?.let {
            when(it) {
                is ErrorResponse -> { }
                is FreshSearchResponse -> recyclerViewDelegate.freshData(it.data)
                is NextPageSearchResponse -> updateList(it.data)
            }
        }
    }

    private fun updateList(results: List<MovieListItemData>) {
        if (recreatedFromSavedState) {
            recyclerViewDelegate.freshData(results)
        } else {
            recyclerViewDelegate.addItems(results)
        }
    }

    companion object {
        const val LIST_STATE = "LIST_STATE"
    }
}
