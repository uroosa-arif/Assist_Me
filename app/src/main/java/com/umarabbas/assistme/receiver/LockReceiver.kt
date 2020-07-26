package com.umarabbas.assistme.receiver

import android.app.admin.DevicePolicyManager
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent

class LockReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val deviceManager = context?.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        val componentName = ComponentName(context, DeviceAdmin::class.java)
        if(deviceManager.isAdminActive(componentName)){
                deviceManager.lockNow()
            }

    }
}