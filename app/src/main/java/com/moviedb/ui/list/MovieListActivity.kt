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

    @Inject
    lateinit var movieListViewModelFactory: MovieListViewModelFactory
    lateinit var movieListViewModel: MovieListViewModel

    @Inject
    lateinit var searchViewModelFactory: SearchViewModelFactory
    lateinit var searchViewModel: SearchViewModel

    lateinit private var searchItem: MenuItem
    lateinit private var searchView: SearchView
    lateinit private var yearSpinner: Spinner
    private var queryText: String = ""
    private var searchOpen: Boolean = false
    private var savedSpinnerPosition: Int = 0
    private val years = mutableListOf<String>()

    override fun getActivity(): BaseFragmentActivity = this

    override fun getLayoutId(): Int = R.layout.activity_movie_list

    override fun getContainerId(): Int = R.id.movie_list_container

    override fun getContentFragment(): BaseFragment = MovieListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModels()
        queryText = savedInstanceState?.getString(QUERY, "") ?: ""
        searchOpen = savedInstanceState?.getBoolean(SEARCH_OPEN, false) ?: false
        savedSpinnerPosition = savedInstanceState?.getInt(YEAR, 0) ?: 0
        setUpYearSpinner(savedSpinnerPosition)
        if (savedInstanceState == null) {
            movieListViewModel.getAllMovies(getSelectedYear())
        }
    }

    private fun initViewModels() {
        movieListViewModel = ViewModelProviders
                .of(this, movieListViewModelFactory)
                .get(MovieListViewModel::class.java)

        searchViewModel = ViewModelProviders
                .of(this, searchViewModelFactory)
                .get(SearchViewModel::class.java)
                .apply {
                    response.observe(
                            this@MovieListActivity,
                            Observer { onLiveDataUpdated(it) }
                    )
                }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        searchItem = menu.findItem(R.id.menu_item_search)
        searchItem.setOnActionExpandListener(this@MovieListActivity)
        searchView = searchItem.actionView as SearchView
        searchViewModel.searchMovies(RxSearchView.queryTextChanges(searchView))

        if (searchOpen) {
            searchItem.expandActionView()
            searchView.setQuery(queryText, false)
        }

        return true
    }

    override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
        searchViewModel.setYear(getSelectedYear())
        return true
    }

    override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
        supportFragmentManager.popBackStack()
        movieListViewModel.getAllMovies(getSelectedYear())
        searchViewModel.clearQuery()
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(YEAR, yearSpinner.selectedItemPosition)
        outState.putString(QUERY, searchView.query?.toString())
        outState.putBoolean(SEARCH_OPEN, searchItem.isActionViewExpanded)
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
        if (savedSpinnerPosition != position) {
            if (searchDisplayed()) {
                searchViewModel.searchMovies(years[position].toIntOrNull())
            } else {
                movieListViewModel.getAllMovies(years[position].toIntOrNull())
            }
        }
    }

    private fun getSelectedYear(): Int? {
        return (yearSpinner.selectedItem as String).toIntOrNull()
    }

    companion object {
        const val YEAR = "YEAR_KEY"
        const val QUERY = "QUERY_KEY"
        const val SEARCH_OPEN = "SEARCHVIEW OPEN"
    }
}
