package com.fomichev.messagelist_kotlincodechallenge.domain

import androidx.lifecycle.Transformations.map
import com.fomichev.messagelist_kotlincodechallenge.database.DatabaseMessage
import com.fomichev.messagelist_kotlincodechallenge.network.NetworkMessageContainer

data class MessageModel(
    val id: String,
    val time: String,
    val text: String)


fun List<MessageModel>.asDatabaseModel(): List<DatabaseMessage> {
    return map {
        DatabaseMessage(
                id = it.id,
                time = it.time,
                text = it.text)
    }
}
