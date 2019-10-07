package com.zrz.android.spacebook.repository.message

import com.google.firebase.iid.FirebaseInstanceId
import com.zrz.android.spacebook.model.firebase.NetworkManager
import com.zrz.android.spacebook.model.firebase.NetworkManagerImpl.Companion.FCM_TOPIC_NAME
import org.json.JSONObject

class MessageRepositoryImpl(private val networkManager: NetworkManager) : MessageRepository {

    override fun connectChat(userName: String) {
        networkManager.subscribeTopic()
        sendNewMessage(userName, USER_JOINED_THE_CHAT, true)
    }

    override fun disconnectChat(userName: String) {
        sendNewMessage(userName, USER_LEFT_THE_CHAT, true)
        networkManager.unsubscribeTopic()
    }

    override fun sendNewMessage(userName: String, text: String, isSystemInformation: Boolean) {
        networkManager.sendMessage(createNewMessage(userName, text, isSystemInformation))
    }

    private fun createNewMessage(
        userName: String,
        text: String,
        isSystemInformation: Boolean
    ): JSONObject {
        val jsonData = JSONObject()
        jsonData.put(KEY_INSTANCE_ID, FirebaseInstanceId.getInstance().id)
        jsonData.put(KEY_IS_SYSTEM_INFORMATION, isSystemInformation)
        jsonData.put(KEY_USER_NAME, userName)
        jsonData.put(KEY_TEXT, text)
        val jsonMessage = JSONObject()
        jsonMessage.put(KEY_DATA, jsonData)
        jsonMessage.put(KEY_TO, TOPICS)
        return jsonMessage
    }

    companion object {
        const val KEY_INSTANCE_ID = "instanceId"
        const val KEY_IS_SYSTEM_INFORMATION = "isSystemInformation"
        const val KEY_USER_NAME = "userName"
        const val KEY_TEXT = "text"
        const val KEY_DATA = "data"
        const val KEY_TO = "to"

        private const val USER_JOINED_THE_CHAT = "joined the chat"
        private const val USER_LEFT_THE_CHAT = "left the chat"
        private const val TOPICS = "/topics/$FCM_TOPIC_NAME"
    }
}