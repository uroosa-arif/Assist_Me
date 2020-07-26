package com.umarabbas.assistme.screens

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.umarabbas.assistme.R
import com.umarabbas.assistme.Utils
import com.umarabbas.assistme.adapters.ReminderRVAdapter
import com.umarabbas.assistme.databinding.ActivityReminderBinding
import com.umarabbas.assistme.models.ReminderTasks
import com.umarabbas.assistme.viewModels.ReminderTasksViewModel
import com.umarabbas.assistme.viewModels.ReminderTasksViewModelFactory
import java.util.*

class ReminderActivity : AppCompatActivity() {
    lateinit var binding: ActivityReminderBinding
    var date = ""
    var title = ""
    var repeatSpinner = ""
    val REQUEST_CODE_SPEECH = 100
    private val newTaskActivityRequestCode = 1
    lateinit var viewModel: ReminderTasksViewModel
    lateinit var adapter : ReminderRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reminder)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.addBottomBtn.visibility = View.GONE
        binding.floatingActionButton.setOnClickListener{
            val intent = Intent(this,AddReminderActivity::class.java)
            startActivityForResult(intent,newTaskActivityRequestCode)
        }
        binding.micButton.setOnClickListener{
            SpeechFunction()
        }
        binding.searchEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.isNotEmpty()){
                    binding.micButton.visibility = View.GONE
                    binding.addBottomBtn.visibility = View.VISIBLE
                }else{
                    binding.micButton.visibility = View.VISIBLE
                    binding.addBottomBtn.visibility = View.GONE
                }
            }

        })
        binding.addBottomBtn.setOnClickListener{
            viewModel.insertTask(ReminderTasks(id = System.currentTimeMillis()/1000,title = binding.searchEt.text.toString(),date = "",timeInMillis = 0,repeat = "",time = ""))
            binding.searchEt.setText("")
            binding.searchEt.clearFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.searchEt.windowToken,0)
        }

        viewModel = ViewModelProvider(this, ReminderTasksViewModelFactory(application)).get(ReminderTasksViewModel::class.java)
        adapter = ReminderRVAdapter(this,viewModel)
        binding.itemsTodoRv.adapter = adapter
        binding.itemsTodoRv.layoutManager = LinearLayoutManager(this)

        viewModel.allReminderTasks.observe(this, Observer {tasks ->
            tasks?.let {
                adapter.setTasks(it)
//                Log.d("test","$it setTask() called")
                if(it.isNotEmpty()){
                    if(date.isNotEmpty()){
                        Utils.setAlarm(this,Utils.timeInMillis,title,it[0].id,repeatSpinner)
                    }
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == newTaskActivityRequestCode && resultCode == Activity.RESULT_OK){
            title  = data?.getStringExtra("title") as String
            val time = data.getStringExtra("time") as String
            date = data.getStringExtra("date") as String
            repeatSpinner = data.getStringExtra("repeatSpinner") as String
            val task = ReminderTasks(id = System.currentTimeMillis()/1000 ,title = title,time = time,date = date,repeat = repeatSpinner,timeInMillis = Utils.timeInMillis)
            viewModel.insertTask(task)
        }else if(requestCode == REQUEST_CODE_SPEECH && resultCode == Activity.RESULT_OK && data!=null){
            val res : ArrayList<String> = data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            binding.searchEt.setText(res[0])
        }
    }

    private fun SpeechFunction(){
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say Something...")
        try {
            startActivityForResult(intent,REQUEST_CODE_SPEECH)
        }catch (exp : ActivityNotFoundException){
            Toast.makeText(this,"Speech not supported", Toast.LENGTH_SHORT).show()
        }
    }
}
