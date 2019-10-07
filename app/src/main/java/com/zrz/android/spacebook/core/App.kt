package com.zrz.android.spacebook.core

import androidx.multidex.MultiDexApplication
import com.zrz.android.spacebook.core.di.AppDI

class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        AppDI.setup(this)
    }
}