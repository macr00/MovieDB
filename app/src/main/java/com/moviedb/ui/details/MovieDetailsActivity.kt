package com.moviedb.ui.details


import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.moviedb.R
import com.moviedb.ui.base.BaseFragment
import com.moviedb.ui.base.BaseFragmentActivity
import javax.inject.Inject

class MovieDetailsActivity : BaseFragmentActivity() {

    @Inject
    lateinit var viewModelFactory: MovieDetailsViewModelFactory

    companion object {
        const val ID_KEY = "ID"
        const val TITLE_KEY = "TITLE"
        fun createIntent(context: Context, id: Long, title: String) =
                Intent(context, MovieDetailsActivity::class.java)
                        .apply {
                            putExtra(ID_KEY, id)
                            putExtra(TITLE_KEY, title)
                        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = intent.getStringExtra(TITLE_KEY)
        }
        ViewModelProviders.of(this, viewModelFactory)
                .get(MovieDetailsViewModel::class.java)
                .getMovie(intent.getLongExtra(ID_KEY, -1))
    }

    override fun getActivity(): BaseFragmentActivity = this

    override fun getLayoutId(): Int = R.layout.activity_movie_detail

    override fun getContainerId(): Int = R.id.movie_detail_container

    override fun getContentFragment(): BaseFragment = MovieDetailsFragment()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

}
