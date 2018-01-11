package com.moviedb.ui.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
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
import kotlinx.android.synthetic.main.activity_movie_list.*
import java.util.*
import javax.inject.Inject

class MovieListActivity : BaseFragmentActivity(),
        MenuItem.OnActionExpandListener, AdapterView.OnItemSelectedListener {

    companion object {
        const val YEAR = "YEAR_KEY"
    }

    @Inject
    lateinit var movieListViewModelFactory: MovieListViewModelFactory
    lateinit private var movieListViewModel: MovieListViewModel

    @Inject
    lateinit var searchViewModelFactory: SearchViewModelFactory
    lateinit private var searchViewModel: SearchViewModel
    lateinit private var searchView: SearchView

    lateinit var yearSpinner: Spinner
    val years = mutableListOf<String>()

    override fun getActivity(): BaseFragmentActivity = this

    override fun getLayoutId(): Int = R.layout.activity_movie_list

    override fun getContainerId(): Int = R.id.movie_list_container

    override fun getContentFragment(): BaseFragment = MovieListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val year = savedInstanceState?.getInt(YEAR, 0) ?: 0
        setUpYearSpinner(year)
        movieListViewModel = ViewModelProviders
                .of(this, movieListViewModelFactory)
                .get(MovieListViewModel::class.java)
    }

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(YEAR, yearSpinner.selectedItemPosition)
    }

    private fun onLiveDataUpdated(response: Response?) {
        response?.let {
            when (it) {
                is ErrorResponse -> {
                }
                is MovieListResponse -> {
                    addSearchFragment()
                }
            }
        }
    }

    private fun addSearchFragment() {
        if (!searchDisplayed()) {
            addFragment(getContainerId(), SearchFragment())
        }
    }

    private fun searchDisplayed(): Boolean {
        return supportFragmentManager.findFragmentById(getContainerId()) is SearchFragment
    }

    private fun setUpYearSpinner(position: Int) {
        (1890..Calendar.getInstance().get(Calendar.YEAR)).mapTo(years) { it.toString() }
        years.add(getString(R.string.all))
        years.reverse()

        val arrayAdapter = ArrayAdapter<String>(this, R.layout.year_spinner_item, years)
        arrayAdapter.setDropDownViewResource(R.layout.year_spinner_dropdown)
        yearSpinner = year_spinner.apply {
            adapter = arrayAdapter
            setSelection(position)
            onItemSelectedListener = this@MovieListActivity
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        // Do nothing
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        val year = convertYearToInt(years[position])
        if (searchDisplayed()) {
            searchViewModel.searchMovies(year)
        } else {
            movieListViewModel.getAllMovies(year)
        }
    }

    private fun convertYearToInt(year: String): Int? {
        return try {
            year.toInt()
        } catch (e: NumberFormatException) {
            // All years is selected
            null
        }
    }

}
