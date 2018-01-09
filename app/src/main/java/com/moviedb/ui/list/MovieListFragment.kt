package com.moviedb.ui.list


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.moviedb.R
import com.moviedb.ui.base.BaseFragment
import com.moviedb.ui.common.ErrorResponse
import com.moviedb.ui.common.MovieListResponse
import com.moviedb.ui.common.Response
import com.moviedb.ui.details.MovieDetailActivity
import kotlinx.android.synthetic.main.fragment_movie_list.*
import javax.inject.Inject


class MovieListFragment : BaseFragment() {

    @Inject
    lateinit var movieListViewModelFactory: MovieListViewModelFactory
    lateinit private var movieListViewModel: MovieListViewModel
    lateinit private var moviesAdapter: MovieListAdapter
    lateinit private var llm: LinearLayoutManager

    override val fragment: BaseFragment = this

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        llm = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        moviesAdapter = MovieListAdapter({
            startActivity(MovieDetailActivity.createIntent(activity, it.id, it.title))
        })
        movies_rv.apply {
            layoutManager = llm
            adapter = moviesAdapter
        }
        movieListViewModel = ViewModelProviders
                .of(activity, movieListViewModelFactory)
                .get(MovieListViewModel::class.java)
                .apply { getAllMovies() }
        movieListViewModel.response.observe(this, Observer { onLiveDataUpdated(it) })
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
            if (it.page == 1) moviesAdapter.newList(it.results) else moviesAdapter.addItems(it.results)
        }
    }

}
