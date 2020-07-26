package com.umarabbas.assistme.screens

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.speech.RecognizerIntent
import android.telephony.SubscriptionManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.umarabbas.assistme.R
import com.umarabbas.assistme.Utils
import com.umarabbas.assistme.databinding.ActivityAddAutoMessageBinding
import java.util.*

class AddAutoMessageActivity : AppCompatActivity() {
    lateinit var binding : ActivityAddAutoMessageBinding
    val requestForContacts = 12
    val REQUEST_CODE_SPEECH = 100
    var year = 0
    var month = 0
    var day = 0
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_auto_message)



        binding.selectPersonBtn.setOnClickListener{
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
            startActivityForResult(intent, requestForContacts)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE_SPEECH && resultCode == Activity.RESULT_OK && data!=null){
            val res : ArrayList<String>? = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            binding.messageEt.setText(res!![0])
        }
        else if(requestCode == requestForContacts){
            if(resultCode == Activity.RESULT_OK){
                val contactsData : Uri? = data?.data
                val cursor =  applicationContext.contentResolver.query(contactsData!!,null,null,null,null)
                cursor!!.moveToFirst();
                val number= cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                //contactName.setText(name);
                binding.personToSendMsg.setText(number);
                //contactEmail.setText(email);
                cursor.close()
            }
            }
        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_reminder_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_add){
            val replyIntent = Intent()
            if(binding.dateEt.text.isNotEmpty() && binding.timeEt.text.isNotEmpty() && binding.personToSendMsg.text.isNotEmpty()&&binding.messageEt.text.isNotEmpty()){
                replyIntent.putExtra("time",binding.timeEt.text.toString())
                replyIntent.putExtra("date",binding.dateEt.text.toString())
                replyIntent.putExtra("message",binding.messageEt.text.toString())
                replyIntent.putExtra("number",binding.personToSendMsg.text.toString())
                replyIntent.putExtra("nmbrOfMsg",binding.numberOfMsg.text.toString())
                replyIntent.putExtra("repeatSpinner",binding.spinner.selectedItem.toString())
                if(binding.numberOfMsg.text.toString().isNotEmpty()){
                    replyIntent.putExtra("limit", binding.numberOfMsg.text.toString())
                }else{
                    replyIntent.putExtra("limit", "1")
                }
                setResult(Activity.RESULT_OK,replyIntent)
                finish()
            }
            else{
                Toast.makeText(this,"Some fields are required", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
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
            }, year, month, day
        )
        datePickerDialod.show()

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
