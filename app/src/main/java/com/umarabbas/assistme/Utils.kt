package com.umarabbas.assistme

import com.umarabbas.assistme.receiver.NotificationReceiver

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.umarabbas.assistme.receiver.LockReceiver
import com.umarabbas.assistme.receiver.MessageReceiver
import com.umarabbas.assistme.receiver.SilentReceiver
import java.io.File


class Utils {
    companion object{
        var timeInMillis : Long = 0


        fun setAlarm(context: Context, timeOfAlarm: Long , title : String , id : Long, repeat: String) {

            val broadcastIntent = Intent(context
                , NotificationReceiver::class.java)
            broadcastIntent.putExtra("title" , title)

            val pIntent = PendingIntent.getBroadcast(
                context,
                id.toInt(),
                broadcastIntent,
                0
            )

            // Setting up AlarmManager
            val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            if (System.currentTimeMillis() < timeOfAlarm) {
                if(repeat == "No Repeat"){
                    alarmMgr.set(
                        AlarmManager.RTC_WAKEUP,
                        timeOfAlarm,
                        pIntent
                    )
                }else{
                    alarmMgr.setRepeating(
                        AlarmManager.RTC_WAKEUP,
                        timeOfAlarm,
                        getTimeForRepeat(repeat),
                        pIntent
                    )
                }
            }
        }

        fun cancelAlarm(context: Context, id : Long){

            val broadcastIntent = Intent(context, NotificationReceiver::class.java)

            val pIntent = PendingIntent.getBroadcast(
                context,
                id.toInt(),
                broadcastIntent,
                0
            )
            val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmMgr.cancel(pIntent)
            Log.d("test","Cancelled $id")
        }

        fun setSilent(context: Context, timeOfAlarm: Long, id : Long, repeat: String){
//            val file = File(context.getExternalFilesDir(null), "file2.txt")
//            if(file.exists()){
//                file.delete()
//            }
//            file.createNewFile()
//            file.appendText("val broadcastIntent = Intent(context, SilentReceiver::class.java)")
            val broadcastIntent = Intent(context, SilentReceiver::class.java)
//            file.appendText("val pIntent = PendingIntent.getBroadcast(context, id.toInt(), broadcastIntent, 0)")
            val pIntent = PendingIntent.getBroadcast(context, id.toInt(), broadcastIntent, 0)

            // Setting up AlarmManager
//            file.appendText("val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager")
            val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

//            file.appendText("Before If else")
            if (System.currentTimeMillis() < timeOfAlarm) {
                if(repeat == "No Repeat"){
                    alarmMgr.set(
                        AlarmManager.RTC_WAKEUP,
                        timeOfAlarm,
                        pIntent
                    )
//                    file.appendText("if executed")
                }else{
                    alarmMgr.setRepeating(
                        AlarmManager.RTC_WAKEUP,
                        timeOfAlarm,
                        getTimeForRepeat(repeat),
                        pIntent
                    )
//                    file.appendText("else executed")
                }
//                file.appendText("After if else")
            }
        }
        fun cancelSilent(context: Context, id : Long){
            val broadcastIntent = Intent(context, SilentReceiver::class.java)

            val pIntent = PendingIntent.getBroadcast(
                context,
                id.toInt(),
                broadcastIntent,
                0
            )
            val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmMgr.cancel(pIntent)
            Log.d("test","Cancelled $id")
        }

        fun setLock(context: Context, timeOfAlarm: Long, id : Long){
            val broadcastIntent = Intent(context
                , LockReceiver::class.java)

            val pIntent = PendingIntent.getBroadcast(
                context,
                id.toInt(),
                broadcastIntent,
                0
            )

            // Setting up AlarmManager
            val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            if (System.currentTimeMillis() < timeOfAlarm) {
                alarmMgr.set(
                    AlarmManager.RTC_WAKEUP,
                    timeOfAlarm,
                    pIntent
                )
            }
        }
        fun cancelLock(context: Context, id: Long){
            val broadcastIntent = Intent(context, LockReceiver::class.java)
            val pIntent = PendingIntent.getBroadcast(
                context,
                id.toInt(),
                broadcastIntent,
                0
            )
            val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmMgr.cancel(pIntent)
            Log.d("test","Cancelled $id")
        }
        fun setMessage(context: Context, timeOfAlarm: Long , msg : String ,number : String, id : Long,repeat: String, nmbrOfMsg : Int){
            val broadcastIntent = Intent(context
                , MessageReceiver::class.java)
            broadcastIntent.putExtra("message" , msg)
            broadcastIntent.putExtra("number" , number)
            broadcastIntent.putExtra("nmbrOfMsg" , nmbrOfMsg)

            val pIntent = PendingIntent.getBroadcast(
                context,
                id.toInt(),
                broadcastIntent,
                0
            )

            // Setting up AlarmManager
            val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            if (System.currentTimeMillis() < timeOfAlarm) {
                if(repeat == "No Repeat"){
                    alarmMgr.set(
                        AlarmManager.RTC_WAKEUP,
                        timeOfAlarm,
                        pIntent
                    )
                }else{
                    alarmMgr.setRepeating(
                        AlarmManager.RTC_WAKEUP,
                        timeOfAlarm,
                        getTimeForRepeat(repeat),
                        pIntent
                    )
                }
            }
        }
        fun cancelMessage(context: Context, id : Long){
            val broadcastIntent = Intent(context, MessageReceiver::class.java)

            val pIntent = PendingIntent.getBroadcast(
                context,
                id.toInt(),
                broadcastIntent,
                0
            )
            val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmMgr.cancel(pIntent)
            Log.d("test","Cancelled $id")
        }
        fun getTimeForRepeat(repeat : String) : Long{
            return when(repeat){
                "Daily" -> 24*60*60*1000
                "Weekly" -> 7*24*60*60*1000
                else -> 0
            }
        }

    }
}