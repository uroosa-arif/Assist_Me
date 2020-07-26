package com.umarabbas.assistme

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.umarabbas.assistme.databinding.ActivityMainBinding
import com.umarabbas.assistme.receiver.DeviceAdmin
import com.umarabbas.assistme.screens.AutoLockActivity
import com.umarabbas.assistme.screens.AutoMessageActivity
import com.umarabbas.assistme.screens.AutoSilentActivity
import com.umarabbas.assistme.screens.ReminderActivity

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var deviceManager : DevicePolicyManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val componentName = ComponentName(this, DeviceAdmin::class.java)
        val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName)
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION , "You should enable the app!" )
        deviceManager = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        startActivityForResult(intent , 1)

        binding.reminderBtn.setOnClickListener{
            val intent = Intent(this , ReminderActivity::class.java)
            startActivity(intent)
        }
        binding.autoMsgBtn.setOnClickListener{
            val intent = Intent(this , AutoMessageActivity::class.java)
            startActivity(intent)
        }
        binding.autoSilentBtn.setOnClickListener{
            val intent = Intent(this , AutoSilentActivity::class.java)
            startActivity(intent)
        }
        binding.aboutBtn.setOnClickListener{
//            val deviceManager = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
//            if(deviceManager.isAdminActive(componentName)){
//                deviceManager.lockNow()
//            }
            val intent = Intent(this , AutoLockActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.remove_as_admin_btn){
            val componentName = ComponentName(this, DeviceAdmin::class.java)
            if(deviceManager.isAdminActive(componentName)){
                deviceManager.removeActiveAdmin(componentName)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
