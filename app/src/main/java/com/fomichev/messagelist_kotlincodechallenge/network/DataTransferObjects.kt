package com.fomichev.messagelist_kotlincodechallenge.network

import com.fomichev.messagelist_kotlincodechallenge.database.DatabaseMessage
import com.fomichev.messagelist_kotlincodechallenge.domain.MessageModel
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkMessageContainer(val networkMessages: List<NetworkMessage>)

@JsonClass(generateAdapter = true)
data class NetworkMessage(
    val id: String,
    val time: String,
    val text: String)

fun NetworkMessageContainer.asDomainModel(): List<MessageModel> {
    return networkMessages.map {
        MessageModel(
            id = it.id,
            time = it.time,
            text = it.text)
    }
}

fun NetworkMessageContainer.asDatabaseModel(): List<DatabaseMessage> {
    return networkMessages.map {
        DatabaseMessage(
            id = it.id,
            time = it.time,
            text = it.text)
    }
}

