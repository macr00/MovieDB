package com.moviedb.ui.list


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.moviedb.R
import com.moviedb.ui.base.BaseFragment
import javax.inject.Inject


class MovieListFragment : BaseFragment() {

    @Inject
    lateinit var movieListViewModelFactory: MovieListViewModelFactory
    lateinit var movieListViewModel: MovieListViewModel
    lateinit var moviesAdapter: MovieListAdapter

    override val fragment: BaseFragment = this

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieListViewModel = ViewModelProviders
                .of(activity, movieListViewModelFactory)
                .get(MovieListViewModel::class.java)
                .apply { getAllMovies() }
    }
}
