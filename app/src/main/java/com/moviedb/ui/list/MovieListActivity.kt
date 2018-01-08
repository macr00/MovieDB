package com.moviedb.ui.list

import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getActivity(): BaseFragmentActivity = this

    override fun getLayoutId(): Int = R.layout.activity_movie_list

    override fun getContainerId(): Int = R.id.movie_list_container

    override fun getContentFragment(): Fragment = MovieListFragment()

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchItem: MenuItem = menu.findItem(R.id.menu_item_search)
                .apply { setOnActionExpandListener(this@MovieListActivity) }
        searchView = searchItem.actionView as SearchView
        movieListViewModel.searchMovies(RxSearchView
                .queryTextChanges(searchView)
                .toFlowable(BackpressureStrategy.LATEST)
        )

        return true
    }

    override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return true
    }

    override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return true
    }
}
