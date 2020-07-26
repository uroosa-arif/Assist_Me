package com.umarabbas.assistme.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_auto_message")
data class AutoMessage(
    @PrimaryKey val id : Long,
    val msg : String,
    val number : String,
    val time : String,
    val date : String,
    val nmbrOfMsg : Int,
    val repeat : String,
    val timeInMillis : Long
)