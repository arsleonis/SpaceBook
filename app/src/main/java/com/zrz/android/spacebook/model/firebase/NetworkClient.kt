package com.zrz.android.spacebook.model.firebase

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object NetworkClient {
    private const val CONNECT_TIMEOUT = 30L
    private const val WRITE_TIMEOUT = 30L
    private const val READ_TIMEOUT = 30L

    private val client = OkHttpClient.Builder()
        .apply {
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        }
        .build()

    fun getClient(): OkHttpClient = client
}