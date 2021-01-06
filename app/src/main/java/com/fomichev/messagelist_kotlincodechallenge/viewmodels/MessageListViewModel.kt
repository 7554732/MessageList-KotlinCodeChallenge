package com.fomichev.messagelist_kotlincodechallenge.viewmodels

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.fomichev.messagelist_kotlincodechallenge.database.getDatabase
import com.fomichev.messagelist_kotlincodechallenge.domain.MessageModel
import com.fomichev.messagelist_kotlincodechallenge.repository.MessagesBoundaryCallback
import com.fomichev.messagelist_kotlincodechallenge.repository.MessagesRepository
import kotlinx.coroutines.launch
import java.io.IOException

class MessageListViewModel(application: Application) : AndroidViewModel(application) {


    private val messagesRepository = MessagesRepository(getDatabase(application))

    private val boundaryCallback = MessagesBoundaryCallback(this)

    val messages: LiveData<PagedList<MessageModel>>
            = messagesRepository.messages.toLiveData(
                pageSize = 10,
                boundaryCallback = boundaryCallback)

    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown


    init {
        refreshDataFromRepository()
    }


     fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                messagesRepository.refreshMessages()
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false

            } catch (networkError: IOException) {
                if(messages.value.isNullOrEmpty())
                    _eventNetworkError.value = true
            }
        }
    }

    fun loadNewMessages(itemAtEnd: MessageModel) {
        //  load New messages only if MessageList scrolled to the End
        if(!messages.value.isNullOrEmpty()) {
            val lastMessage = messages.value?.last()
            if(itemAtEnd != lastMessage) return
        }

        viewModelScope.launch {
            try {
                messagesRepository.loadNewMessages()
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false

            } catch (networkError: IOException) {
                if(messages.value.isNullOrEmpty())
                    _eventNetworkError.value = true
            }
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    fun deleteMessagesFromRepository(messages: List<MessageModel>){
        viewModelScope.launch {
            messagesRepository.deleteMessageFromDB(messages)
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MessageListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MessageListViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
