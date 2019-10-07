package com.zrz.android.spacebook.feature.chat

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.zrz.android.spacebook.R
import com.zrz.android.spacebook.core.base.BaseActivity
import com.zrz.android.spacebook.util.extension.getRequiredStringFromIntent
import com.zrz.android.spacebook.util.extension.useInputText
import com.zrz.android.spacebook.util.helper.EndSnapHelper
import kotlinx.android.synthetic.main.activity_chat.*
import org.koin.android.ext.android.inject

class ChatActivity : BaseActivity() {

    private val viewModel: ChatViewModel by inject()

    override fun obtainLayoutResID(): Int = R.layout.activity_chat

    private lateinit var chatAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btnSendMessage.setOnClickListener {
            useInputText(etNewMessageText, { viewModel.sendMessage(it) })
        }
        recyclerViewInitializing()
        observeLiveData()
        viewModel.subscribeObservable()
        val userName = getRequiredStringFromIntent(KEY_LOGIN, KEY_NAME)
        viewModel.initializeUserName(userName)
        viewModel.connectChat()
    }

    override fun onDestroy() {
        viewModel.disconnectChat()
        super.onDestroy()
    }

    private fun recyclerViewInitializing() {
        val chatLayoutManager = LinearLayoutManager(applicationContext)
        chatLayoutManager.stackFromEnd
        chatAdapter = ChatAdapter()
        rvChat.apply {
            layoutManager = chatLayoutManager
            adapter = chatAdapter
        }
        val snapHelper: LinearSnapHelper = EndSnapHelper()
        snapHelper.attachToRecyclerView(rvChat)
    }

    private fun observeLiveData() {
        observingAction(viewModel.throwableLiveData, { handleError(it) })
        observingAction(viewModel.chatLiveData, { chatAdapter.updateChat(it) })
    }

    companion object {
        const val KEY_LOGIN = "key_login"
        const val KEY_NAME = "key_name"
        const val BUNDLE_KEY = "key_bundle"
        const val INTENT_KEY = "key_intent"
    }
}
