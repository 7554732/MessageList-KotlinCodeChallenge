package com.fomichev.messagelist_kotlincodechallenge.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fomichev.messagelist_kotlincodechallenge.R
import com.fomichev.messagelist_kotlincodechallenge.databinding.MessageItemBinding
import com.fomichev.messagelist_kotlincodechallenge.domain.MessageModel

class MessageListAdapter(val messageClick: MessageClick, val messageLongClick: MessageLongClick) : PagedListAdapter<MessageModel, MessageViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<MessageModel>() {
            override fun areItemsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean =
                oldItem == newItem
        }
    }

    var multiSelect: Boolean = false

    val selectedMessages: MutableSet<MessageModel> = mutableSetOf<MessageModel>()

    fun notifySelectedItemsChanged() {
        for(sel in selectedMessages){
            currentList?.indexOf(sel)?.let { notifyItemChanged(it) }
        }
    }

    fun isSelected(message: MessageModel?): Boolean {
        return selectedMessages.contains(message)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val withDataBinding:MessageItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            MessageViewHolder.LAYOUT,
            parent,
            false)
        return MessageViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.viewDataBinding.also {
            val message = getItem(position)
            it.message = message
            it.isSelected = isSelected(message)
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
    fun onClick(message: MessageModel) {
           block(message)
    }
}

class MessageLongClick(val block: (MessageModel) -> Unit) {
    fun onLongClick(message: MessageModel): Boolean  {
        block(message)
        return true
    }
}

