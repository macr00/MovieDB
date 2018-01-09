package com.moviedb.ui.base

import android.content.Context
import android.support.v4.app.Fragment
import com.moviedb.ui.common.Response
import dagger.android.support.AndroidSupportInjection


abstract class BaseFragment: Fragment() {

    abstract val fragment: BaseFragment

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(fragment)
        super.onAttach(context)
        retainInstance = true
    }

    abstract fun onLiveDataUpdated(response: Response?)

}