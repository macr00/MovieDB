package com.moviedb.ui.list

import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView
import com.moviedb.R
import com.moviedb.ui.base.BaseFragmentActivity
import io.reactivex.BackpressureStrategy
import javax.inject.Inject

class MovieListActivity : BaseFragmentActivity(), MenuItem.OnActionExpandListener {

    @Inject
    lateinit var movieListViewModelFactory: MovieListViewModelFactory
    lateinit var movieListViewModel: MovieListViewModel
    lateinit var searchView: SearchView

    override fun getActivity(): BaseFragmentActivity = this

    override fun getLayoutId(): Int = R.layout.activity_movie_list

    override fun getContainerId(): Int = R.id.movie_list_container

    override fun getContentFragment(): Fragment = MovieListFragment()

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchItem: MenuItem = menu.findItem(R.id.menu_item_search)
        searchItem.setOnActionExpandListener(this@MovieListActivity)
        searchView = searchItem.actionView as SearchView
        movieListViewModel = ViewModelProviders
                .of(this, movieListViewModelFactory)
                .get(MovieListViewModel::class.java)
                .apply {
                    searchMovies(RxSearchView
                            .queryTextChanges(searchView)
                            .toFlowable(BackpressureStrategy.LATEST))
                }

        return true
    }

    override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
        // TODO check this
        return true
    }

    override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
        //TODO implement cancel search
        return true
    }
}
