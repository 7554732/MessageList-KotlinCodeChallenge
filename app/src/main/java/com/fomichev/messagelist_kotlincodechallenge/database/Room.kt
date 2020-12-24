package com.fomichev.messagelist_kotlincodechallenge.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface MessageDao {
    @Query("select * from databasemessage")
    fun getMessages(): LiveData<List<DatabaseMessage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( messages: List<DatabaseMessage>)

    @Delete
    fun delete(messages: List<DatabaseMessage>)
}



@Database(entities = [DatabaseMessage::class], version = 1)
abstract class MessagesDatabase: RoomDatabase() {
    abstract val messageDao: MessageDao
}

private lateinit var INSTANCE: MessagesDatabase

fun getDatabase(context: Context): MessagesDatabase {
    synchronized(MessagesDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                MessagesDatabase::class.java,
                "messages").build()
        }
    }
    return INSTANCE
}
