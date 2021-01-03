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
        /**
         * This diff callback informs the PagedListAdapter how to compute list differences when new
         * PagedLists arrive.
         * <p>
         * When you add a Cheese with the 'Add' button, the PagedListAdapter uses diffCallback to
         * detect there's only a single item difference from before, so it only needs to animate and
         * rebind a single view.
         *
         * @see DiffUtil
         */
        private val diffCallback = object : DiffUtil.ItemCallback<MessageModel>() {
            override fun areItemsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean =
                oldItem.id == newItem.id

            /**
             * Note that in kotlin, == checking on data classes compares all contents, but in Java,
             * typically you'll implement Object#equals, and use it to compare object contents.
             */
            override fun areContentsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean =
                oldItem == newItem
        }
    }

    val selectedMessages: List<MessageModel>
        get() {
            return selectedItems.map{messages.get(it)}
        }

    var messages: List<MessageModel> = emptyList()
        set(value) {
            if(multiSelect) return
            field = value
            notifyDataSetChanged()
        }

    var multiSelect: Boolean = false

    val selectedItems: MutableSet<Int> = mutableSetOf<Int>()

    fun isSelected(position: Int): Boolean {
        return selectedItems.contains(position)
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
            it.position = position
            it.message = messages[position]
            it.isSelected = isSelected(position)
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

class MessageClick(val block: (Int) -> Unit) {
    fun onClick(position: Int) {
           block(position)
    }
}

class MessageLongClick(val block: (Int) -> Unit) {
    fun onLongClick(position: Int): Boolean  {
        block(position)
        return true
    }
}

