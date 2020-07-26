package com.umarabbas.assistme.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_reminder_tasks")
data class ReminderTasks(
    @PrimaryKey val id : Long,
    val title : String,
    val time : String,
    val date : String,
    val repeat : String,
    val timeInMillis : Long
)