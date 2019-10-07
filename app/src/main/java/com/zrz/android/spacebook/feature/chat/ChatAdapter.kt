package com.zrz.android.spacebook.feature.chat

import android.view.View
import android.view.ViewGroup
import com.zrz.android.spacebook.R
import com.zrz.android.spacebook.core.base.BaseAdapter
import com.zrz.android.spacebook.entity.MemberSBMessage
import com.zrz.android.spacebook.entity.OwnSBMessage
import com.zrz.android.spacebook.entity.SBMessage
import com.zrz.android.spacebook.entity.SystemSBMessage
import com.zrz.android.spacebook.util.extension.inflateView
import kotlinx.android.synthetic.main.item_member.view.*
import kotlinx.android.synthetic.main.item_own.view.*
import kotlinx.android.synthetic.main.item_system.view.*

class ChatAdapter : BaseAdapter<SBMessage, ChatAdapter.MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder =
        when (viewType) {
            SYSTEM_MESSAGE -> MessageViewHolder.SystemMessageViewHolder(parent.inflateView(R.layout.item_system))
            OWN_MESSAGE -> MessageViewHolder.OwnMessageViewHolder(parent.inflateView(R.layout.item_own))
            MEMBER_MESSAGE -> MessageViewHolder.MemberMessageViewHolder(parent.inflateView(R.layout.item_member))
            else -> throw IllegalArgumentException()
        }

    override fun getItemViewType(position: Int): Int =
        when (messages[position]) {
            is SystemSBMessage -> SYSTEM_MESSAGE
            is OwnSBMessage -> OWN_MESSAGE
            is MemberSBMessage -> MEMBER_MESSAGE
        }

    override fun compareItemsById(): ((Pair<SBMessage, SBMessage>) -> Boolean) =
        { messagePair -> messagePair.first.time == messagePair.second.time }

    sealed class MessageViewHolder(view: View) : BaseAdapter.BaseViewHolder<SBMessage>(view) {

        class SystemMessageViewHolder(view: View) : MessageViewHolder(view) {
            override fun onBind(message: SBMessage) {
                itemView.tvSystemMessageTime.text = message.time
                itemView.tvSystemMessageText.text = message.text
            }
        }

        class OwnMessageViewHolder(view: View) : MessageViewHolder(view) {
            override fun onBind(message: SBMessage) {
                itemView.tvOwnMessageTime.text = message.time
                itemView.tvOwnMessageName.text = message.author
                itemView.tvOwnMessageText.text = message.text
            }
        }

        class MemberMessageViewHolder(view: View) : MessageViewHolder(view) {
            override fun onBind(message: SBMessage) {
                itemView.tvMemberMessageTime.text = message.time
                itemView.tvMemberMessageName.text = message.author
                itemView.tvMemberMessageText.text = message.text
            }
        }
    }

    companion object {
        const val SYSTEM_MESSAGE = 0
        const val OWN_MESSAGE = 1
        const val MEMBER_MESSAGE = 2
    }
}