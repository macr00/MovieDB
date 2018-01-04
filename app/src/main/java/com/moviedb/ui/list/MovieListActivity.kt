package com.moviedb.ui.list

import android.os.Bundle
import android.support.v4.app.Fragment
import com.moviedb.R
import com.moviedb.ui.base.BaseFragmentActivity

class MovieListActivity : BaseFragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // TODO maybe switch to val properties
    override fun getLayoutId(): Int = R.layout.activity_movie_list

    override fun getContainerId(): Int = R.id.movie_list_container

    override fun getContentFragment(): Fragment = MovieListFragment()
}
