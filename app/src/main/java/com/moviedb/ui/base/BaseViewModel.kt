package com.moviedb.ui.base

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.moviedb.ui.common.Response
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    protected val disposables = CompositeDisposable()
    protected val liveData = MutableLiveData<Response>()

    val response: LiveData<Response>
        get() = liveData

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    protected fun onError(throwable: Throwable) {

    }
}

