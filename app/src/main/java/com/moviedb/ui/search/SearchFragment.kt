package com.moviedb.ui.search


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.moviedb.R
import com.moviedb.data.model.MovieListItemData
import com.moviedb.ui.base.BaseFragment
import com.moviedb.ui.common.ErrorResponse
import com.moviedb.ui.common.MovieListResponse
import com.moviedb.ui.common.RecyclerViewDelegate
import com.moviedb.ui.common.Response
import com.moviedb.ui.details.MovieDetailActivity
import com.moviedb.ui.list.MovieListAdapter
import kotlinx.android.synthetic.main.fragment_movie_list.*
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
                lm = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false),
                listAdapter = MovieListAdapter(mutableListOf(), {
                    startActivity(MovieDetailActivity.createIntent(activity, it.id, it.title))
                })
        )
        searchViewModel = ViewModelProviders
                .of(activity, searchViewModelFactory)
                .get(SearchViewModel::class.java)
                .apply {
                    response.observe(this@SearchFragment, Observer { onLiveDataUpdated(it) })
                }
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
