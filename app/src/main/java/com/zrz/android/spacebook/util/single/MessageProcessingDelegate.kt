package com.zrz.android.spacebook.util.single

import com.zrz.android.spacebook.core.observer.BaseObservable
import com.zrz.android.spacebook.core.observer.Observer
import com.zrz.android.spacebook.entity.SBMessage

object MessageProcessingDelegate : BaseObservable<SBMessage, Observer<SBMessage>>()