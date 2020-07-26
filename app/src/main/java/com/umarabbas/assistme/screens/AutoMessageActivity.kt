package com.umarabbas.assistme.screens

import android.Manifest
import android.Manifest.permission.SEND_SMS
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.umarabbas.assistme.R
import com.umarabbas.assistme.Utils
import com.umarabbas.assistme.adapters.MessageRVAdapter
import com.umarabbas.assistme.databinding.ActivityAutoMessageBinding
import com.umarabbas.assistme.models.AutoMessage
import com.umarabbas.assistme.viewModels.MessageViewModel
import com.umarabbas.assistme.viewModels.MessageViewModelFactory


class AutoMessageActivity : AppCompatActivity() {
    lateinit var binding : ActivityAutoMessageBinding
    private val newTaskActivityRequestCode = 1
    var date = ""
    var time = ""
    var msg = ""
    var number = ""
    var nmbrOfMsg = 1
    var repeatSpinner = ""
    private val requestForActivity: Int = 1
    lateinit var viewModel : MessageViewModel
    lateinit var adapter : MessageRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auto_message)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.floatingActionButton.setOnClickListener{
            if(isSMSPermissionGranted()){
                val intent = Intent(this, AddAutoMessageActivity::class.java)
                startActivityForResult(intent,newTaskActivityRequestCode)
            }
        }
        viewModel = ViewModelProvider(this, MessageViewModelFactory(application)).get(MessageViewModel::class.java)
        adapter = MessageRVAdapter(this,viewModel)
        binding.itemsTodoRv.adapter = adapter
        binding.itemsTodoRv.layoutManager = LinearLayoutManager(this)
        viewModel.allMessageTasks.observe(this, Observer {tasks ->
            tasks?.let {
                adapter.setTasks(it)
//                Log.d("test","$it setTask() called")
                if(it.isNotEmpty()){
                    if(date.isNotEmpty() && time.isNotEmpty() && msg.isNotEmpty() && number.isNotEmpty()){
                        Utils.setMessage(this, Utils.timeInMillis,it[0].msg,it[0].number,it[0].id,it[0].repeat, it[0].nmbrOfMsg)
                    }
                }
            }
        })
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == requestForActivity && resultCode == Activity.RESULT_OK){
            time = data?.getStringExtra("time") as String
            date = data.getStringExtra("date") as String
            msg = data.getStringExtra("message") as String
            number = data.getStringExtra("number") as String
            nmbrOfMsg = (data.getStringExtra("nmbrOfMsg") as String).toInt()
            val noOfMsg = data.getStringExtra("limit") as String
            repeatSpinner = data.getStringExtra("repeatSpinner") as String
            val task = AutoMessage(id = System.currentTimeMillis()/1000,msg = msg ,number = number ,time = time,date = date,nmbrOfMsg = noOfMsg.toInt() ,repeat = repeatSpinner,timeInMillis = Utils.timeInMillis)
            viewModel.insertTask(task)
        }
    }

    private fun isSMSPermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(SEND_SMS)
                == PackageManager.PERMISSION_GRANTED
            ) {
                true
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.SEND_SMS),
                    0
                )
                false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == 0){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                val intent = Intent(this , AddAutoMessageActivity::class.java)
                startActivityForResult(intent , requestForActivity)
            }else{
                Toast.makeText(this, "You can not add Message without permission", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}
