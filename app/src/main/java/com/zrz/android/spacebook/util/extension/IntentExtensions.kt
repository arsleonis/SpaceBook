package com.zrz.android.spacebook.util.extension

import android.content.Intent

fun Intent.getRequiredString(intentKey: String, bundleKey: String) =
    getBundleExtra(intentKey.asIntentKey())?.getString(bundleKey.asBundleKey())!!