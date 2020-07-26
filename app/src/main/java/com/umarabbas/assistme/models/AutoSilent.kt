package com.umarabbas.assistme.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_auto_silent")
data class AutoSilent(
    @PrimaryKey val id : Long,
    val time : String,
    val date : String,
    val repeat : String,
    val timeInMillis : Long
)