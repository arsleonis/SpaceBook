package com.zrz.android.spacebook.util.extension

import com.zrz.android.spacebook.BuildConfig.APPLICATION_ID
import com.zrz.android.spacebook.feature.chat.ChatActivity.Companion.BUNDLE_KEY
import com.zrz.android.spacebook.feature.chat.ChatActivity.Companion.INTENT_KEY

fun String.asBundleKey() = "$APPLICATION_ID$BUNDLE_KEY$this"

fun String.asIntentKey() = "$APPLICATION_ID$INTENT_KEY$this"
