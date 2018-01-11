package com.moviedb.ui.search


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
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
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

class SearchFragment : BaseFragment() {

    @Inject
    lateinit var searchViewModelFactory: SearchViewModelFactory
    lateinit private var searchViewModel: SearchViewModel
    lateinit private var recyclerViewDelegate: RecyclerViewDelegate<MovieListItemData>

    override val fragment: BaseFragment = this

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    override fun onLiveDataUpdated(response: Response?) {
        response?.let {
            when(it) {
                is ErrorResponse -> { }
                is MovieListResponse -> { updateList(it) }
            }
        }
    }

    private fun updateList(response: MovieListResponse) {
        response.data.let {
            if (it.page == 1) {
                recyclerViewDelegate.freshData(it.results)
            } else {
                recyclerViewDelegate.addItems(it.results)
            }
        }
    }

    companion object {
        const val TAG = "SearchFragment"
    }
}
