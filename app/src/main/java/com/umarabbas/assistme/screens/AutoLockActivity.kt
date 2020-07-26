package com.umarabbas.assistme.screens

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.umarabbas.assistme.R
import com.umarabbas.assistme.Utils
import com.umarabbas.assistme.adapters.LockRVAdapter
import com.umarabbas.assistme.databinding.ActivityAutoLockBinding
import com.umarabbas.assistme.models.AutoLock
import com.umarabbas.assistme.viewModels.LockViewModel
import com.umarabbas.assistme.viewModels.LockViewModelFactory

class AutoLockActivity : AppCompatActivity() {
    lateinit var binding : ActivityAutoLockBinding
    var date = ""
    var time = ""
    private val requestForActivity: Int = 1
    lateinit var viewModel : LockViewModel
    lateinit var adapter : LockRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this , R.layout.activity_auto_lock)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.floatingActionButton.setOnClickListener{
            val intent = Intent(this , AddAutoLockActivity::class.java)
            startActivityForResult(intent, requestForActivity)
        }
        viewModel = ViewModelProvider(this, LockViewModelFactory(application)).get(LockViewModel::class.java)
        adapter = LockRVAdapter(this,viewModel)
        binding.itemsTodoRv.adapter = adapter
        binding.itemsTodoRv.layoutManager = LinearLayoutManager(this)
        viewModel.allLockTasks.observe(this, Observer {tasks ->
            tasks?.let {
                adapter.setTasks(it)
//                Log.d("test","$it setTask() called")
                if(it.isNotEmpty()){
                    if(date.isNotEmpty() && time.isNotEmpty()){
                        Utils.setLock(this,Utils.timeInMillis,it[0].id)
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
            val task = AutoLock(id = System.currentTimeMillis()/1000,time = time,date = date,timeInMillis = Utils.timeInMillis)
            viewModel.insertTask(task)
        }
    }
}
