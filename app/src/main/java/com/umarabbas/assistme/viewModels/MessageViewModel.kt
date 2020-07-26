package com.umarabbas.assistme.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.umarabbas.assistme.database.AutoMessageRepository
import com.umarabbas.assistme.database.TasksDatabase
import com.umarabbas.assistme.models.AutoMessage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MessageViewModel(application: Application) : AndroidViewModel(application) {

    var msgTasksRepository : AutoMessageRepository
    lateinit var li : LiveData<List<AutoMessage>>
    var allMessageTasks: LiveData<List<AutoMessage>>

    init {
        val db = TasksDatabase.getDatabase(application,viewModelScope)
        val msgDao = db.msgDao()

        msgTasksRepository = AutoMessageRepository(msgDao)
        allMessageTasks = msgTasksRepository.allTasks
    }

    fun getTasks(){
        li = allMessageTasks
    }

    fun insertTask(task: AutoMessage) =
        GlobalScope.launch {
            msgTasksRepository.insert(task)
            Log.d("test","insertTask called $task")
        }


    fun deleteTask(task: AutoMessage) =
        GlobalScope.launch {
            msgTasksRepository.delete(task)
        }
}