package com.zrz.android.spacebook.util.extension

import androidx.lifecycle.MutableLiveData

fun <L, D> MutableLiveData<L>.addItem(vararg data: D)
    where L : MutableList<D> {
    val list = listOf(*data)
    list.forEach { this.value!!.add(it) }
    this.postValue(this.value)
}