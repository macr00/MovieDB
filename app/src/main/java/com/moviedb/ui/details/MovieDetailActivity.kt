package com.moviedb.ui.details

import android.os.Bundle
import android.support.v4.app.Fragment
import com.moviedb.R
import com.moviedb.ui.base.BaseFragmentActivity

class MovieDetailActivity : BaseFragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getActivity(): BaseFragmentActivity = this

    override fun getLayoutId(): Int = R.layout.activity_movie_detail

    override fun getContainerId(): Int = R.id.movie_detail_container

    override fun getContentFragment(): Fragment = MovieDetailsFragment()
}
