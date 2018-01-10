package com.moviedb.ui.list


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
import com.moviedb.ui.common.*
import com.moviedb.ui.details.MovieDetailActivity
import kotlinx.android.synthetic.main.fragment_movie_list.*
import javax.inject.Inject


class MovieListFragment : BaseFragment() {

    @Inject
    lateinit var movieListViewModelFactory: MovieListViewModelFactory
    lateinit private var movieListViewModel: MovieListViewModel
    lateinit private var recyclerViewDelegate: RecyclerViewDelegate<MovieListItemData>

    override val fragment: BaseFragment = this

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewDelegate = RecyclerViewDelegate(
                recyclerView = movies_rv,
                llm = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false),
                listAdapter = MovieListAdapter(mutableListOf(), {
                    startActivity(MovieDetailActivity.createIntent(activity, it.id, it.title))
                })
        )
        movieListViewModel = ViewModelProviders
                .of(activity, movieListViewModelFactory)
                .get(MovieListViewModel::class.java)
                .apply {
                    getAllMovies()
                    response.observe(this@MovieListFragment, Observer { onLiveDataUpdated(it) })
                }

        movies_rv.addOnScrollListener(InfiniteScrollListener({ movieListViewModel.loadNextPage() }))
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

}
