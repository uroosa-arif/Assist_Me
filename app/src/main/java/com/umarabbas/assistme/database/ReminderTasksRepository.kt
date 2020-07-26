package com.umarabbas.assistme.database

import androidx.lifecycle.LiveData
import com.umarabbas.assistme.models.ReminderTasks

class ReminderTasksRepository (private val tasksDao: ReminderTasksDao){

    val allTasks : LiveData<List<ReminderTasks>> = tasksDao.getAllTasks()

    suspend fun insert(reminderTasks: ReminderTasks){
        tasksDao.insert(reminderTasks)
    }
    suspend fun update(reminderTasks: ReminderTasks){
        tasksDao.update(reminderTasks)
    }
    suspend fun delete(reminderTasks: ReminderTasks){
        tasksDao.delete(reminderTasks)
    }
}