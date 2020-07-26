package com.umarabbas.assistme.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.umarabbas.assistme.database.ReminderTasksRepository
import com.umarabbas.assistme.database.TasksDatabase
import com.umarabbas.assistme.models.ReminderTasks
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ReminderTasksViewModel(application: Application) : AndroidViewModel(application) {

    var reminderTasksRepository : ReminderTasksRepository
    lateinit var li : LiveData<List<ReminderTasks>>
    var allReminderTasks: LiveData<List<ReminderTasks>>

    init {
        val db = TasksDatabase.getDatabase(application,viewModelScope)
        val taskListsDao = db.tasksDao()
        val tasksDao = db.tasksDao()

        reminderTasksRepository = ReminderTasksRepository(tasksDao)
        allReminderTasks = reminderTasksRepository.allTasks
    }

    fun getTasks(){
        li = allReminderTasks
    }

    fun insertTask(task: ReminderTasks) =
        GlobalScope.launch {
            reminderTasksRepository.insert(task)
            Log.d("test","insertTask called $task")
        }


    fun deleteTask(task: ReminderTasks) =
        GlobalScope.launch {
            reminderTasksRepository.delete(task)
        }
}