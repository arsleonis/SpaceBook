package com.zrz.android.spacebook.model.firebase

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.zrz.android.spacebook.core.observer.Observable
import com.zrz.android.spacebook.core.observer.Observer
import com.zrz.android.spacebook.entity.MemberSBMessage
import com.zrz.android.spacebook.entity.OwnSBMessage
import com.zrz.android.spacebook.entity.SBMessage
import com.zrz.android.spacebook.entity.SystemSBMessage
import com.zrz.android.spacebook.util.extension.getCurrentTime
import com.zrz.android.spacebook.util.single.MessageProcessingDelegate
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.net.URL
import java.util.concurrent.Executors

class NetworkManagerImpl : NetworkManager,
    Observable<SBMessage, Observer<SBMessage>> by MessageProcessingDelegate {

    private val client = NetworkClient.getClient()

    override fun subscribeTopic() {
        Log.d("ml", "subscribeTopic() ${FirebaseMessaging.getInstance()}")
        FirebaseMessaging.getInstance().subscribeToTopic(FCM_TOPIC_NAME).addOnCompleteListener{

                    if (!it.isSuccessful) {
                        Log.d("ml","FAILURE");
                    }else{
                        Log.d("ml","SUCCESS");
                    }

            }
    }

    override fun unsubscribeTopic() {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(FCM_TOPIC_NAME)
    }

    override fun sendMessage(message: JSONObject) {
        val executor = Executors.newFixedThreadPool(3)
        executor.execute {
            val url = URL(FCM_URL)
            val mediaType: MediaType = FCM_MEDIA_TYPE.toMediaType()
            val requestBody = message.toString().toRequestBody(mediaType)
            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .header(FCM_AUTHORIZATION_NAME, FCM_AUTHORIZATION_VALUE)
                .build()
            val fcmResponse = client.newCall(request).execute()
            Log.d("ml", "fcmResponse = $fcmResponse")
        }
    }

    override fun addMessageToChat(
        id: String,
        instanceId: String,
        isSystemInformation: Boolean,
        userName: String,
        text: String
    ) {
        val time = getCurrentTime(CHAT_TIME_FORMAT)
        when {
            isSystemInformation -> notifyObserversData(
                SystemSBMessage(id = id, time = time, text = "$userName $text")
            )
            instanceId == FirebaseInstanceId.getInstance().id -> notifyObserversData(
                OwnSBMessage(id, time, userName, text)
            )
            else -> notifyObserversData(MemberSBMessage(id, time, userName, text))
        }
    }

    fun notifyError(throwable: Throwable) {
        notifyObserversError(throwable)
    }

    companion object {
        const val FCM_TOPIC_NAME = "SpaceBookChat"

        private const val CHAT_TIME_FORMAT = "HH:mm:ss"
        private const val FCM_URL = "https://fcm.googleapis.com/fcm/send"
        private const val FCM_MEDIA_TYPE = "application/json"
        private const val FCM_AUTHORIZATION_NAME = "Authorization"
        private const val FCM_AUTHORIZATION_VALUE =
            "key=AAAASp_UWMI:APA91bHon87BVAqu16lwzDsUYBq34gYSCJvxJY28G78byieFJKzkVgjWMgdnJE" +
                "ckZDy6JpvLCSriHktp9cJGeXKED-mm57DSSt1X5x3rqCiL6VrWq5SeYI_cS3NwWNwDQQcp9yu-1Rbu"
    }
}