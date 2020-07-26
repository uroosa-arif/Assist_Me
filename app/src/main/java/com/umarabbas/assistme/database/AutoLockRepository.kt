package com.umarabbas.assistme.database

import androidx.lifecycle.LiveData
import com.umarabbas.assistme.models.AutoLock

class AutoLockRepository (private val tasksDao: AutoLockDao){

    val allTasks : LiveData<List<AutoLock>> = tasksDao.getAllTasks()

    suspend fun insert(lockTask : AutoLock){
        tasksDao.insert(lockTask)
    }
    suspend fun update(lockTask : AutoLock){
        tasksDao.update(lockTask)
    }
    suspend fun delete(lockTask : AutoLock){
        tasksDao.delete(lockTask)
    }
}