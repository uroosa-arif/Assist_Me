package com.umarabbas.assistme.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.umarabbas.assistme.models.ReminderTasks

@Dao
interface ReminderTasksDao {

    @Query("SELECT * from table_reminder_tasks order by id desc")
    fun getAllTasks() : LiveData<List<ReminderTasks>>

    @Query("SELECT * from table_reminder_tasks order by id desc")
    fun getTasks() : List<ReminderTasks>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reminderTasks: ReminderTasks)

    @Update
    suspend fun update(reminderTasks: ReminderTasks)

    @Delete
    suspend fun delete(reminderTasks: ReminderTasks)
}