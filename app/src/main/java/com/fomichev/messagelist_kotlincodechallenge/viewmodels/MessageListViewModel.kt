package com.fomichev.messagelist_kotlincodechallenge.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.fomichev.messagelist_kotlincodechallenge.database.getDatabase
import com.fomichev.messagelist_kotlincodechallenge.repository.MessagesRepository
import kotlinx.coroutines.launch
import java.io.IOException

class MessageListViewModel(application: Application) : AndroidViewModel(application) {


    private val messagesRepository = MessagesRepository(getDatabase(application))

    val messages = messagesRepository.messages

    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    object InputFiles {
        val files = listOf("01.json", "02.json", "03.json")
    }

    init {
        refreshDataFromRepository()
    }


    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                messagesRepository.refreshMessages(InputFiles.files)
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
