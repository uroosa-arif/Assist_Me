package com.umarabbas.assistme.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SilentViewModelFactory (private val application: Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SilentViewModel::class.java)) {
            return SilentViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}