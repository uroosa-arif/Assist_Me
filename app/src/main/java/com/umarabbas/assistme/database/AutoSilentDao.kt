package com.umarabbas.assistme.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.umarabbas.assistme.models.AutoSilent

@Dao
interface AutoSilentDao {

    @Query("SELECT * from table_auto_silent order by id desc")
    fun getAllTasks() : LiveData<List<AutoSilent>>

    @Query("SELECT * from table_auto_silent order by id desc")
    fun getTasks() : List<AutoSilent>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(silentTasks: AutoSilent)

    @Update
    suspend fun update(silentTasks: AutoSilent)

    @Delete
    suspend fun delete(silentTasks: AutoSilent)
}