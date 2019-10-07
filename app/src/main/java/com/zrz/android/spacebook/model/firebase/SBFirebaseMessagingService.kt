package com.zrz.android.spacebook.model.firebase

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.zrz.android.spacebook.entity.SBMessage
import com.zrz.android.spacebook.repository.message.MessageRepositoryImpl
import org.koin.android.ext.android.inject

class SBFirebaseMessagingService : FirebaseMessagingService() {

    private val networkManager: NetworkManager by inject()

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)


        //val moshi: Moshi = Moshi.Builder().build()
        //val moshiAdapter: JsonAdapter<SBMessage> = moshi.adapter(SBMessage::class.java)


        Log.d("ml", "---------------------------------------")
        //Log.d("ml", "remoteMessage = $remoteMessage")
        //Log.d("ml", "messageId = ${remoteMessage.messageId}")
        Log.d("ml", "data = ${remoteMessage.data}")

        val id: String = remoteMessage.messageId ?: ""
        val instanceId = remoteMessage.data[MessageRepositoryImpl.KEY_INSTANCE_ID] ?: ""
        val isSystemInformation = remoteMessage
            .data[MessageRepositoryImpl.KEY_IS_SYSTEM_INFORMATION]!!
            .toBoolean()
        val userName = remoteMessage.data[MessageRepositoryImpl.KEY_USER_NAME] ?: ""
        val text = remoteMessage.data[MessageRepositoryImpl.KEY_TEXT] ?: ""

        networkManager.addMessageToChat(id, instanceId, isSystemInformation, userName, text)
    }
}