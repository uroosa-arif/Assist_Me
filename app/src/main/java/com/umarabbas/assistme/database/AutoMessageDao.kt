package com.umarabbas.assistme.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.umarabbas.assistme.models.AutoMessage

@Dao
interface AutoMessageDao {

    @Query("SELECT * from table_auto_message order by id desc")
    fun getAllTasks() : LiveData<List<AutoMessage>>
    @Query("SELECT * from table_auto_message order by id desc")
    fun getTasks() : List<AutoMessage>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(msgTasks: AutoMessage)

    @Update
    suspend fun update(msgTasks: AutoMessage)

    @Delete
    suspend fun delete(msgTasks: AutoMessage)
}