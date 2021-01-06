package com.fomichev.messagelist_kotlincodechallenge.repository

import androidx.paging.PagedList
import com.fomichev.messagelist_kotlincodechallenge.domain.MessageModel
import com.fomichev.messagelist_kotlincodechallenge.viewmodels.MessageListViewModel

class MessagesBoundaryCallback (private val messageListViewModel: MessageListViewModel): PagedList.BoundaryCallback<MessageModel>() {

        override fun onItemAtEndLoaded(itemAtEnd: MessageModel) {
            messageListViewModel.loadNewMessages(itemAtEnd)
        }
}
