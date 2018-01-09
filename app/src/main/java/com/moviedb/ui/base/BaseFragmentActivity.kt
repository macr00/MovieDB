package com.moviedb.ui.base

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseFragmentActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

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

    abstract fun getContentFragment(): BaseFragment

    protected fun addFragment(containerId: Int, fragment: BaseFragment) {
        supportFragmentManager.beginTransaction().apply {
            add(containerId, fragment, fragment.tag)
            addToBackStack(fragment.tag)
        }.commit()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }
}
