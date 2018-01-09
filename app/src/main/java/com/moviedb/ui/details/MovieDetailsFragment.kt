package com.moviedb.ui.details


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.moviedb.R
import com.moviedb.ui.base.BaseFragment
import com.moviedb.ui.common.Response


class MovieDetailsFragment : BaseFragment() {

    override val fragment: BaseFragment = this

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onLiveDataUpdated(response: Response?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
