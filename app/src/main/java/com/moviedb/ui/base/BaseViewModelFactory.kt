package com.moviedb.ui.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

abstract class BaseViewModelFactory<VM: ViewModel>: ViewModelProvider.Factory {

    protected abstract val viewModel: VM

    protected abstract val vmClass: Class<VM>

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(vmClass)) {
            return viewModel as T
        }

        throw IllegalArgumentException()
    }
}