package com.zrz.android.spacebook.feature.chat

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.zrz.android.spacebook.core.base.BaseViewModel
import com.zrz.android.spacebook.core.observer.Observable
import com.zrz.android.spacebook.core.observer.Observer
import com.zrz.android.spacebook.entity.SBMessage
import com.zrz.android.spacebook.repository.message.MessageRepository
import com.zrz.android.spacebook.util.extension.addItem

class ChatViewModel(
    app: Application,
    private val messageRepository: MessageRepository,
    private val observable: Observable<SBMessage, Observer<SBMessage>>
) : BaseViewModel(app), Observer<SBMessage> {

    val chatLiveData: MutableLiveData<MutableList<SBMessage>> = MutableLiveData()

    private var userName: String? = null

    init {
        chatLiveData.value = mutableListOf()
    }

    override fun onCleared() {
        observable.unsubscribe(this)
    }

    override fun onObserveData(data: SBMessage) {
        updateChatLiveData(data)
    }

    override fun onObserveError(throwable: Throwable) {
        reportError(throwable)
    }

    ///////////////////

    fun initializeUserName(userName: String) {
        if (this.userName == null) this.userName = userName
    }

    fun connectChat() {
        messageRepository.connectChat(userName!!)
    }

    fun disconnectChat() {
        messageRepository.disconnectChat(userName!!)
    }

    //////////////

    fun subscribeObservable() {
        observable.subscribe(this)
    }

    fun sendMessage(text: String) {
        messageRepository.sendNewMessage(userName!!, text)
    }

    private fun updateChatLiveData(message: SBMessage) {
        chatLiveData.addItem(message)
    }
}