package com.umarabbas.assistme.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.umarabbas.assistme.models.AutoLock
import com.umarabbas.assistme.models.AutoMessage
import com.umarabbas.assistme.models.AutoSilent
import com.umarabbas.assistme.models.ReminderTasks
import kotlinx.coroutines.CoroutineScope

@Database(entities = [ReminderTasks::class , AutoSilent::class , AutoLock::class , AutoMessage::class], version = 1, exportSchema = false)
public abstract class TasksDatabase : RoomDatabase() {

    abstract fun tasksDao(): ReminderTasksDao
    abstract fun silentDao(): AutoSilentDao
    abstract fun lockDao(): AutoLockDao
    abstract fun msgDao(): AutoMessageDao

    companion object {
        private lateinit var INSTANCE: TasksDatabase

        fun getDatabase(context: Context, scope: CoroutineScope? = null): TasksDatabase {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    TasksDatabase::class.java,
                    "assist_me_database"
                ).build()
            }
            return INSTANCE
        }
    }
}