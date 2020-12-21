package com.fomichev.messagelist_kotlincodechallenge.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fomichev.messagelist_kotlincodechallenge.domain.MessageModel

@Entity
data class DatabaseMessage constructor(
    @PrimaryKey
    val id: String,
    val time: String,
    val text: String)

fun List<DatabaseMessage>.asDomainModel(): List<MessageModel> {
    return map {
        MessageModel(
            id = it.id,
            time = it.time,
            text = it.text)
    }
}
