package com.moviedb.ui.base

import android.content.Context
import android.support.v4.app.Fragment
import dagger.android.support.AndroidSupportInjection


abstract class BaseFragment: Fragment() {

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(fragment)
        super.onAttach(context)
    }

    abstract val fragment: BaseFragment
}