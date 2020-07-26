package com.umarabbas.assistme.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ReminderTasksViewModelFactory (private val application: Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReminderTasksViewModel::class.java)) {
            return ReminderTasksViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}