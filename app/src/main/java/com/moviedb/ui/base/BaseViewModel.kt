package com.moviedb.ui.base

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel<T>: ViewModel() {

    protected val disposables = CompositeDisposable()
    val liveData: LiveData<T> = MutableLiveData()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}

