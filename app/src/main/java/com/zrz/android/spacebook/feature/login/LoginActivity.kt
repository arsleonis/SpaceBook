package com.zrz.android.spacebook.feature.login

import android.os.Bundle
import com.zrz.android.spacebook.R
import com.zrz.android.spacebook.core.base.BaseActivity
import com.zrz.android.spacebook.feature.chat.ChatActivity
import com.zrz.android.spacebook.feature.chat.ChatActivity.Companion.KEY_LOGIN
import com.zrz.android.spacebook.feature.chat.ChatActivity.Companion.KEY_NAME
import com.zrz.android.spacebook.util.extension.startNewActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    override fun obtainLayoutResID(): Int = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btnConnect.setOnClickListener {
            connectToChat()
        }
    }

    private fun connectToChat() {
        val name = etLoginName.text!!.toString()
        if (name.isNotEmpty()) {
            startNewActivity<ChatActivity>(KEY_LOGIN, KEY_NAME to name)
        } else {
            showToast(getString(R.string.login_field_name_is_empty))
        }
    }
}
