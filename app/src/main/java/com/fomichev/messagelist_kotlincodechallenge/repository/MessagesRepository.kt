package com.fomichev.messagelist_kotlincodechallenge.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.fomichev.messagelist_kotlincodechallenge.database.MessagesDatabase
import com.fomichev.messagelist_kotlincodechallenge.database.asDomainModel
import com.fomichev.messagelist_kotlincodechallenge.domain.MessageModel
import com.fomichev.messagelist_kotlincodechallenge.network.MessageNetwork
import com.fomichev.messagelist_kotlincodechallenge.network.NetworkMessageContainer
import com.fomichev.messagelist_kotlincodechallenge.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.Thread.sleep

class MessagesRepository(private val database: MessagesDatabase) {

    val messages: LiveData<List<MessageModel>> = Transformations.map(database.messageDao.getMessages()) {
        it.asDomainModel()
    }

    suspend fun refreshMessages(fileName:String) {
        withContext(Dispatchers.IO) {
            Timber.d("refresh messages is called");
            sleep(5000)
            val networkMessageContainer = NetworkMessageContainer(MessageNetwork.messageService.getMessages(fileName))
            database.messageDao.insertAll(networkMessageContainer.asDatabaseModel())
        }
    }
}
