package com.umarabbas.assistme.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.umarabbas.assistme.models.AutoLock

@Dao
interface AutoLockDao {

    @Query("SELECT * from table_auto_lock order by id desc")
    fun getAllTasks() : LiveData<List<AutoLock>>

    @Query("SELECT * from table_auto_lock order by id desc")
    fun getTasks() : List<AutoLock>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(lockTasks: AutoLock)

    @Update
    suspend fun update(lockTasks: AutoLock)

    @Delete
    suspend fun delete(lockTasks: AutoLock)
}