package com.zrz.android.spacebook.model.firebase

import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject

interface NetworkManager {

    fun subscribeTopic()

    fun unsubscribeTopic()

    fun addMessageToChat(
        id: String,
        instanceId: String,
        isSystemInformation: Boolean,
        userName: String,
        text: String
    )

    fun sendMessage(message: JSONObject)
}