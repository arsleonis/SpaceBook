package com.zrz.android.spacebook.core.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

abstract class BaseViewModel(app: Application) : AndroidViewModel(app) {

    val throwableLiveData: MutableLiveData<Throwable> = MutableLiveData()

    fun reportError(throwable: Throwable) {
        throwableLiveData.value = throwable
    }
}