package com.zrz.android.spacebook.core.observer

interface Observable<D, in O : Observer<D>> {

    fun subscribe(observer: O)

    fun unsubscribe(observer: O)

    fun isSubscribed(observer: O): Boolean

    fun notifyObserversData(data: D)

    fun notifyObserversError(throwable: Throwable)
}