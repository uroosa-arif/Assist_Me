package com.umarabbas.assistme.screens

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.umarabbas.assistme.R
import com.umarabbas.assistme.Utils
import com.umarabbas.assistme.databinding.ActivityAddReminderBinding
import com.umarabbas.assistme.viewModels.ReminderTasksViewModel
import com.umarabbas.assistme.viewModels.ReminderTasksViewModelFactory
import java.util.*

class AddReminderActivity : AppCompatActivity() {
    lateinit var binding : ActivityAddReminderBinding
    var year = 0
    var month = 0
    var day = 0
    lateinit var viewModel: ReminderTasksViewModel
    val REQUEST_CODE_SPEECH = 100
    val MONTHS = listOf(
        "Jan",
        "Feb",
        "Mar",
        "Apr",
        "May",
        "June",
        "Jul",
        "Aug",
        "Sep",
        "Oct",
        "Nov",
        "Dec"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_reminder)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.timeEt.visibility = View.GONE
        binding.timeBtn.visibility = View.GONE
        binding.repeatTv.visibility = View.GONE
        binding.spinner.visibility = View.GONE

        viewModel = ViewModelProvider(this, ReminderTasksViewModelFactory(application)).get(ReminderTasksViewModel::class.java)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_reminder_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_add){
            val replyIntent = Intent()
            if(binding.titleEt.text.isNotEmpty()){
                replyIntent.putExtra("title",binding.titleEt.text.toString())
                replyIntent.putExtra("time",binding.timeEt.text.toString())
                replyIntent.putExtra("date",binding.dateEt.text.toString())
                replyIntent.putExtra("repeatSpinner",binding.spinner.selectedItem.toString())
                setResult(Activity.RESULT_OK,replyIntent)
                finish()
            }
            else{
                Toast.makeText(this,"You did not add any thing", Toast.LENGTH_SHORT).show()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if(binding.timeEt.text.isNotEmpty() || binding.dateEt.text.isNotEmpty()){
            AlertDialog.Builder(this)
                .setTitle("Are you sure?")
                .setMessage("Quit without saving?")
                .setNegativeButton("cancel",null)
                .setPositiveButton("yes", {dialog, which ->
                    super.onBackPressed()
                }).create().show()
        } else{
            super.onBackPressed()
        }
    }

    fun setTime(view: View) {
        // Get Current Time
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        // Launch Time Picker Dialog
        val timePickerDialog = TimePickerDialog(this,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minuteOfHour ->

                calendar.set(Calendar.YEAR , this.year)
                calendar.set(Calendar.MONTH , this.month)
                calendar.set(Calendar.DAY_OF_MONTH,this.day)
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minuteOfHour)
                calendar.set(Calendar.SECOND, 0)

                val amPm = if (hourOfDay < 12) "am" else "pm"
                val formattedTime = String.format("%02d:%02d %s", hourOfDay, minuteOfHour, amPm)
                binding.timeEt.setText(formattedTime)
                Utils.timeInMillis = calendar.timeInMillis
            }, hour, minute, false
        )
        timePickerDialog.show()

    }
    fun setDate(view: View) {
        // Get Current Time
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialod = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { _, y, m, d ->

                this.year = y
                this.month = m
                this.day = d

                binding.dateEt.setText("" + d + " " + MONTHS[m] + ", " + y)
                binding.timeEt.visibility = View.VISIBLE
                binding.timeBtn.visibility = View.VISIBLE
                binding.repeatTv.visibility = View.VISIBLE
                binding.spinner.visibility = View.VISIBLE
            }, year, month, day
        )
        datePickerDialod.show()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE_SPEECH && resultCode == Activity.RESULT_OK && data!=null){
            val res : ArrayList<String>? = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            binding.titleEt.setText(res!![0])
        }
    }

    fun SpeechFunction(view : View){
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
