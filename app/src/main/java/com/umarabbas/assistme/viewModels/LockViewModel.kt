package com.umarabbas.assistme.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.umarabbas.assistme.database.AutoLockRepository
import com.umarabbas.assistme.database.TasksDatabase
import com.umarabbas.assistme.models.AutoLock
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LockViewModel(application: Application) : AndroidViewModel(application) {

    var lockTasksRepository : AutoLockRepository
    lateinit var li : LiveData<List<AutoLock>>
    var allLockTasks: LiveData<List<AutoLock>>

    init {
        val db = TasksDatabase.getDatabase(application,viewModelScope)
        val lockDao = db.lockDao()

        lockTasksRepository = AutoLockRepository(lockDao)
        allLockTasks = lockTasksRepository.allTasks
    }

    fun getTasks(){
        li = allLockTasks
    }

    fun insertTask(task: AutoLock) =
        GlobalScope.launch {
            lockTasksRepository.insert(task)
            Log.d("test","insertTask called $task")
        }


    fun deleteTask(task: AutoLock) =
        GlobalScope.launch {
            lockTasksRepository.delete(task)
        }
}