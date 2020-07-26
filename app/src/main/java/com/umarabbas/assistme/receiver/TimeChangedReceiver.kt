package com.umarabbas.assistme.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.umarabbas.assistme.Utils
import com.umarabbas.assistme.database.TasksDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TimeChangedReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent?.action == "android.intent.action.TIME_SET"){
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
