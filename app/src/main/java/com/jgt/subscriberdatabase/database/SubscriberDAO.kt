package com.jgt.subscriberdatabase.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SubscriberDAO {
    //suspend since we need to execute in coroutines
    @Insert
    suspend fun insertSubscriber(subscriber: Subscriber): Long

    @Update
    suspend fun updateSubscriber(subscriber: Subscriber)

    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber)

    @Query("DELETE FROM subscriber_data_table")
    suspend fun deleteAll()

    //Room library automatically processes functions with returns as live data type in background
    @Query("SELECT * FROM subscriber_data_table")
    fun getAllSubscribers(): LiveData<List<Subscriber>>
}