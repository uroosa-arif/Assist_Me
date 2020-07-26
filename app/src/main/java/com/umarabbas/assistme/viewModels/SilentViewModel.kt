package com.umarabbas.assistme.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.umarabbas.assistme.database.AutoSilentRepository
import com.umarabbas.assistme.database.TasksDatabase
import com.umarabbas.assistme.models.AutoSilent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class SilentViewModel(application: Application) : AndroidViewModel(application) {

    var silentTasksRepository : AutoSilentRepository
    lateinit var li : LiveData<List<AutoSilent>>
    var allReminderTasks: LiveData<List<AutoSilent>>

    init {
        val db = TasksDatabase.getDatabase(application,viewModelScope)
        val silentDao = db.silentDao()

        silentTasksRepository = AutoSilentRepository(silentDao)
        allReminderTasks = silentTasksRepository.allTasks
    }

    fun getTasks(){
        li = allReminderTasks
    }

    fun insertTask(task: AutoSilent) =
        GlobalScope.launch {
            silentTasksRepository.insert(task)
            Log.d("test","insertTask called $task")
        }


    fun deleteTask(task: AutoSilent) =
        GlobalScope.launch {
            silentTasksRepository.delete(task)
        }
}