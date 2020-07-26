package com.umarabbas.assistme.database

import androidx.lifecycle.LiveData
import com.umarabbas.assistme.models.AutoSilent

class AutoSilentRepository (private val tasksDao: AutoSilentDao){

    val allTasks : LiveData<List<AutoSilent>> = tasksDao.getAllTasks()

    suspend fun insert(silentTask : AutoSilent){
        tasksDao.insert(silentTask)
    }
    suspend fun update(silentTask : AutoSilent){
        tasksDao.update(silentTask)
    }
    suspend fun delete(silentTask : AutoSilent){
        tasksDao.delete(silentTask)
    }
}