package com.fomichev.messagelist_kotlincodechallenge.viewmodels

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.Config
import androidx.paging.toLiveData
import com.fomichev.messagelist_kotlincodechallenge.database.getDatabase
import com.fomichev.messagelist_kotlincodechallenge.domain.MessageModel
import com.fomichev.messagelist_kotlincodechallenge.repository.MessagesRepository
import kotlinx.coroutines.launch
import java.io.IOException

class MessageListViewModel(application: Application) : AndroidViewModel(application) {


    private val messagesRepository = MessagesRepository(getDatabase(application))

    val messages = messagesRepository.messages.toLiveData(
        Config(
        pageSize = 10,
        enablePlaceholders = false,
        maxSize = 100)
    )


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
