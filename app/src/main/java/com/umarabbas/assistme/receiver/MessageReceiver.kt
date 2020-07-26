package com.umarabbas.assistme.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.SmsManager

class MessageReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val number = intent.getStringExtra("number")
        val msg = intent.getStringExtra("message")
        val nmbrOfMsg = intent.getIntExtra("nmbrOfMsg", 1)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
//            val subscriptionManager = context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager
//            val sims = subscriptionManager.activeSubscriptionInfoCountMax
            for(i in 0 until nmbrOfMsg){
                val defaultSim = SmsManager.getDefaultSmsSubscriptionId()
                val smsManager = SmsManager.getSmsManagerForSubscriptionId(defaultSim)
//                val msgArray: ArrayList<String> = smsManager.divideMessage(msg)
//                smsManager.sendMultipartTextMessage(number, null, msgArray, null, null)
                smsManager.sendTextMessage(number, null, msg, null, null)
            }

        } else {
            for(i in 0 until nmbrOfMsg){
                val smsManager = SmsManager.getDefault()
//                val msgArray: ArrayList<String> = smsManager.divideMessage(msg)
//                smsManager.sendMultipartTextMessage(number, null, msgArray, null, null)
                smsManager.sendTextMessage(number, null, msg, null, null)
            }
        }

    }
}
