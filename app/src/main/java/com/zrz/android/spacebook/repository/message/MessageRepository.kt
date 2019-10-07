package com.zrz.android.spacebook.repository.message

interface MessageRepository {

    fun connectChat(userName: String)

    fun disconnectChat(userName: String)

    fun sendNewMessage(userName: String, text: String, isSystemInformation: Boolean = false)
}