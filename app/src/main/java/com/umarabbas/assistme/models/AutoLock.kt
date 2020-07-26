package com.umarabbas.assistme.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_auto_lock")
data class AutoLock(
    @PrimaryKey val id : Long,
    val time : String,
    val date : String,
    val timeInMillis : Long
)