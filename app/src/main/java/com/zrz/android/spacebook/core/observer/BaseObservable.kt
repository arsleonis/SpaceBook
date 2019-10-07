package com.zrz.android.spacebook.core.observer

open class BaseObservable<D, in O : Observer<D>> : Observable<D, O> {
    private val observers: MutableList<Observer<D>> = mutableListOf()

    override fun subscribe(observer: O) {
        observers.add(observer)
    }

    override fun unsubscribe(observer: O) {
        observers.remove(observer)
    }

    override fun isSubscribed(observer: O): Boolean =
        observers.any { true }

    override fun notifyObserversData(data: D) {
        observers.forEach { it.onObserveData(data) }
    }

    override fun notifyObserversError(throwable: Throwable) {
        observers.forEach { it.onObserveError(throwable) }
    }
}