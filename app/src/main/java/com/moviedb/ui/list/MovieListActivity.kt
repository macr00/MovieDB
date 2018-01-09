package com.moviedb.ui.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView
import com.moviedb.R
import com.moviedb.ui.base.BaseFragment
import com.moviedb.ui.base.BaseFragmentActivity
import com.moviedb.ui.common.ErrorResponse
import com.moviedb.ui.common.MovieListResponse
import com.moviedb.ui.common.Response
import com.moviedb.ui.search.SearchFragment
import com.moviedb.ui.search.SearchViewModel
import com.moviedb.ui.search.SearchViewModelFactory
import io.reactivex.BackpressureStrategy
import javax.inject.Inject

class MovieListActivity : BaseFragmentActivity(), MenuItem.OnActionExpandListener {

    @Inject
    lateinit var searchViewModelFactory: SearchViewModelFactory
    lateinit private var searchViewModel: SearchViewModel
    lateinit private var searchView: SearchView

    override fun getActivity(): BaseFragmentActivity = this

    override fun getLayoutId(): Int = R.layout.activity_movie_list

    override fun getContainerId(): Int = R.id.movie_list_container

    override fun getContentFragment(): BaseFragment = MovieListFragment()

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchItem: MenuItem = menu.findItem(R.id.menu_item_search)
        searchItem.setOnActionExpandListener(this@MovieListActivity)
        searchView = searchItem.actionView as SearchView
        searchViewModel = ViewModelProviders
                .of(this, searchViewModelFactory)
                .get(SearchViewModel::class.java)
                .apply {
                    searchMovies(RxSearchView
                            .queryTextChanges(searchView)
                            .toFlowable(BackpressureStrategy.LATEST))
                }

        searchViewModel.response.observe(this, Observer { onLiveDataUpdated(it) })

        return true
    }

    override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
        return true
    }

    override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
        supportFragmentManager.popBackStack()
        return true
    }

    private fun onLiveDataUpdated(response: Response?) {
        response?.let {
            when (it) {
                is ErrorResponse -> { }
                is MovieListResponse -> { addSearchFragment() }
            }
        }
    }

    private fun addSearchFragment() {
        if (supportFragmentManager.findFragmentById(getContainerId()) is SearchFragment) {
            return
        }

        addFragment(getContainerId(), SearchFragment())
    }
}
