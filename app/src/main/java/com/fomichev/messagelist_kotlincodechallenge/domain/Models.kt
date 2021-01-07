package com.fomichev.messagelist_kotlincodechallenge.domain

import com.fomichev.messagelist_kotlincodechallenge.database.DatabaseMessage
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

data class MessageModel(
    val id: String,
    val time: String,
    val text: String){

    val formattedTime: String
        get() {
            val date = Date(time.toLong())
            val format: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val formatted: String = format.format(date)
            return formatted
        }
}


fun List<MessageModel>.asDatabaseModel(): List<DatabaseMessage> {
    return map {
        DatabaseMessage(
                id = it.id,
                time = it.time,
                text = it.text)
    }
}
