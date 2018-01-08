package com.moviedb.ui.base

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import dagger.android.AndroidInjection

abstract class BaseFragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(getActivity())
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        if (savedInstanceState == null) {
            addFragment(getContainerId(), getContentFragment())
        }
    }

    abstract fun getActivity(): BaseFragmentActivity

    abstract fun getLayoutId(): Int

    abstract fun getContainerId(): Int

    abstract fun getContentFragment(): Fragment

    private fun addFragment(containerId: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            add(containerId, fragment)
        }.commit()
    }
}
