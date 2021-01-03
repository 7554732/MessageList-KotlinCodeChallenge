package com.fomichev.messagelist_kotlincodechallenge.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.fomichev.messagelist_kotlincodechallenge.database.MessagesDatabase
import com.fomichev.messagelist_kotlincodechallenge.database.asDomainModel
import com.fomichev.messagelist_kotlincodechallenge.domain.MessageModel
import com.fomichev.messagelist_kotlincodechallenge.domain.asDatabaseModel
import com.fomichev.messagelist_kotlincodechallenge.network.MessageNetwork
import com.fomichev.messagelist_kotlincodechallenge.network.NetworkInputDataManager
import com.fomichev.messagelist_kotlincodechallenge.network.NetworkMessageContainer
import com.fomichev.messagelist_kotlincodechallenge.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MessagesRepository(private val database: MessagesDatabase) {

    val messages = database.messageDao.getMessages().mapByPage {
        it.asDomainModel()
    }


    suspend fun refreshMessages() {
        val files:List<String>? = NetworkInputDataManager.files
        if(files == null) return
        withContext(Dispatchers.IO) {
            for(file:String in files){
                val networkMessageContainer = NetworkMessageContainer(MessageNetwork.messageService.getMessages(file))
                database.messageDao.insertAll(networkMessageContainer.asDatabaseModel())
            }
        }
    }

    suspend fun deleteMessageFromDB(messages: List<MessageModel>) {
        withContext(Dispatchers.IO) {
            database.messageDao.delete(messages.asDatabaseModel())
        }
    }
}
