package com.zrz.android.spacebook.core.observer

interface Observer<D> {

    fun onObserveData(data: D)

    fun onObserveError(throwable: Throwable)
}