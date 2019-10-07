package com.zrz.android.spacebook.util.extension

import java.text.SimpleDateFormat
import java.util.*

fun getCurrentTime(dateFormat: String): String =
    SimpleDateFormat(dateFormat, Locale.ROOT).format(GregorianCalendar.getInstance().time)