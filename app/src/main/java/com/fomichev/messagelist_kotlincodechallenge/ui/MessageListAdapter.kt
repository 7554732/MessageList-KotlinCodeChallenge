package com.fomichev.messagelist_kotlincodechallenge.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.fomichev.messagelist_kotlincodechallenge.R
import com.fomichev.messagelist_kotlincodechallenge.databinding.MessageItemBinding
import com.fomichev.messagelist_kotlincodechallenge.domain.MessageModel

class MessageListAdapter(val messageClick: MessageClick, val messageLongClick: MessageLongClick) : RecyclerView.Adapter<MessageViewHolder>() {

    var messages: List<MessageModel> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var multiSelect: Boolean = false

    val selectedMessages: MutableList<MessageModel> = mutableListOf<MessageModel>()

    fun isSelected(message: MessageModel): Boolean {
        return false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val withDataBinding:MessageItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            MessageViewHolder.LAYOUT,
            parent,
            false)
        return MessageViewHolder(withDataBinding)
    }

    override fun getItemCount() = messages.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.message = messages[position]
            it.messageClick = messageClick
            it.messageLongClick = messageLongClick
        }
    }

}

class MessageViewHolder(val viewDataBinding:MessageItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.message_item
    }
}

class MessageClick(val block: (MessageModel) -> Unit) {
    fun onClick(message: MessageModel) = block(message)
}

class MessageLongClick(val block: (MessageModel) -> Unit) {
    fun onLongClick(view: View, message: MessageModel): Boolean  {
        block(message)
        return true
    }
}

