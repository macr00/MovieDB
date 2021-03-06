package com.moviedb.ui.details


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.moviedb.R
import com.moviedb.domain.model.MovieDetailData
import com.moviedb.ui.base.BaseFragment
import com.moviedb.ui.common.ErrorResponse
import com.moviedb.ui.common.MovieDetailsResponse
import com.moviedb.ui.common.Response
import kotlinx.android.synthetic.main.fragment_movie_details.*
import javax.inject.Inject


class MovieDetailsFragment : BaseFragment() {


    @Inject
    lateinit var viewModelFactory: MovieDetailsViewModelFactory

    override val fragment: BaseFragment = this

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewModelProviders.of(activity, viewModelFactory)
                .get(MovieDetailsViewModel::class.java)
                .let {
                    it.response.observe(this@MovieDetailsFragment, Observer {
                        onLiveDataUpdated(it)
                    })
                }
    }

    override fun onLiveDataUpdated(response: Response?) {
        response?.let {
            when (it) {
                is ErrorResponse -> {
                }
                is MovieDetailsResponse -> {
                    displayDetails(it.data)
                }
            }
        }
    }

    private fun displayDetails(data: MovieDetailData) {
        if (data.tagline.isNotEmpty()) tagline.text = data.tagline else tagline.visibility = View.GONE
        overview.text = data.overview
        release_date.text = getString(R.string.release_date, data.releaseDate)
        average_rating.text = getString(R.string.ave_rating, data.voteAverage.toString(), data.voteCount)
        Log.d("Genres", data.genres.toString())

        if (data.genres.isEmpty()) {
            genres.visibility = View.GONE
        } else {
            val sb = StringBuilder()
            data.genres.forEach {
                sb.append(", " + it.name)
            }
            val genreList = sb.toString().replaceFirst(", ", "")
            genres.text = getString(R.string.genres, genreList)
        }
    }
}
