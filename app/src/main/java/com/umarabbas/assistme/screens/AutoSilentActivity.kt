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
import com.umarabbas.assistme.adapters.SilentRVAdapter
import com.umarabbas.assistme.databinding.ActivityAutoSilentBinding
import com.umarabbas.assistme.models.AutoSilent
import com.umarabbas.assistme.viewModels.SilentViewModel
import com.umarabbas.assistme.viewModels.SilentViewModelFactory

class AutoSilentActivity : AppCompatActivity() {
    var date = ""
    var time = ""
    var repeatSpinner= ""
    private val requestForActivity: Int = 1
    lateinit var binding : ActivityAutoSilentBinding
    lateinit var viewModel : SilentViewModel
    lateinit var adapter : SilentRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this , R.layout.activity_auto_silent)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.floatingActionButton.setOnClickListener{
            val intent = Intent(this , AddAutoSilentActivity::class.java)
            startActivityForResult(intent, requestForActivity)
        }
        viewModel = ViewModelProvider(this, SilentViewModelFactory(application)).get(SilentViewModel::class.java)
        adapter = SilentRVAdapter(this,viewModel)
        binding.itemsTodoRv.adapter = adapter
        binding.itemsTodoRv.layoutManager = LinearLayoutManager(this)
        viewModel.allReminderTasks.observe(this, Observer {tasks ->
            tasks?.let {
                adapter.setTasks(it)
//                Log.d("test","$it setTask() called")
                if(it.isNotEmpty()){
                    if(date.isNotEmpty() && time.isNotEmpty()){
                        Utils.setSilent(this,Utils.timeInMillis,it[0].id,repeatSpinner)
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
            repeatSpinner = data.getStringExtra("repeatSpinner") as String
            val task = AutoSilent(id = System.currentTimeMillis()/1000,time = time,date = date,repeat = repeatSpinner,timeInMillis = Utils.timeInMillis)
            viewModel.insertTask(task)
        }
    }
}
