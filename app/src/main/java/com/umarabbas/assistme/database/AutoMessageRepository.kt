package com.umarabbas.assistme.database

import androidx.lifecycle.LiveData
import com.umarabbas.assistme.models.AutoMessage

class AutoMessageRepository (private val tasksDao: AutoMessageDao){

    val allTasks : LiveData<List<AutoMessage>> = tasksDao.getAllTasks()

    suspend fun insert(msgTask : AutoMessage){
        tasksDao.insert(msgTask)
    }
    suspend fun update(msgTask : AutoMessage){
        tasksDao.update(msgTask)
    }
    suspend fun delete(msgTask : AutoMessage){
        tasksDao.delete(msgTask)
    }
}