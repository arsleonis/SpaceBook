package com.zrz.android.spacebook.core.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zrz.android.spacebook.util.extension.addItemDiff

abstract class BaseAdapter<M, VH : BaseAdapter.BaseViewHolder<M>>(
    val messages: MutableList<M> = mutableListOf()
) : RecyclerView.Adapter<VH>() {

    override fun getItemCount() = messages.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (isInPositionsRange(position)) holder.onBind(messages[position])
    }

    fun updateChat(messagesLD: List<M>) {
        if (messagesLD.size > messages.size) {
            addItemDiff(messages, messagesLD, compareItemsById())
            addItem(messagesLD)
        }
    }

    open fun addItem(newMessages: List<M>) {
        val oldLastIndex = messages.lastIndex
        newMessages.forEachIndexed { index, item -> if (index > oldLastIndex) messages.add(item) }
    }

    private fun isInPositionsRange(position: Int) =
        position != RecyclerView.NO_POSITION && position < itemCount

    abstract fun compareItemsById(): (Pair<M, M>) -> Boolean

    abstract class BaseViewHolder<M>(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun onBind(message: M)
    }
}