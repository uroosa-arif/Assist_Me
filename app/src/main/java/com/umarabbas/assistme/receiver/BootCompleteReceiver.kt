package com.umarabbas.assistme.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.umarabbas.assistme.MainActivity
import com.umarabbas.assistme.Utils
import com.umarabbas.assistme.database.TasksDatabase
import com.umarabbas.assistme.models.AutoSilent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

class BootCompleteReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action == "android.intent.action.BOOT_COMPLETED"){
//            val file = File(context.getExternalFilesDir(null) , "boot.txt")
//            file.createNewFile()

            GlobalScope.launch {
                val db = TasksDatabase.getDatabase(context)
                val silentDao = db.silentDao()
                val lockDao = db.lockDao()
                val messageDao = db.msgDao()
                val reminderDao = db.tasksDao()

                val lockTasks = lockDao.getTasks()
                val messageTasks = messageDao.getTasks()
                val reminderTasks = reminderDao.getTasks()
                val silentTasks = silentDao.getTasks()
                for(silent in silentTasks){
                    Utils.setSilent(context, silent.timeInMillis, silent.id, silent.repeat)
                }


                for(lock in lockTasks){
                    Utils.setLock(context, lock.timeInMillis, lock.id)
                }
                for(message in messageTasks){
                    Utils.setMessage(context, message.timeInMillis, message.msg, message.number, message.id, message.repeat, message.nmbrOfMsg)
                }
                for(reminder in reminderTasks){
                    Utils.setAlarm(context, reminder.timeInMillis, reminder.title, reminder.id,reminder.repeat)
                }
            }

        }
    }
}
