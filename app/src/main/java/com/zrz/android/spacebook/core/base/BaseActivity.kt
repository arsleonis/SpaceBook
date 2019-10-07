package com.zrz.android.spacebook.core.base

import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

abstract class BaseActivity : AppCompatActivity() {

    @LayoutRes
    abstract fun obtainLayoutResID(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(obtainLayoutResID())
    }

    fun showToast(text: String, gravity: Int = Gravity.BOTTOM) {
        val toast = Toast.makeText(this, text, Toast.LENGTH_SHORT)
        toast.setGravity(gravity, 0, 0)
        toast.show()
    }

    fun handleError(throwable: Throwable) {
        showToast(throwable.toString())
        throwable.message?.let { showToast(it) }
    }

    inline fun <reified L : MutableLiveData<T>, T> observingAction(
        liveData: L,
        noinline callback: ((T) -> Unit)
    ) {
        liveData.observe(this, Observer(callback))
    }
}