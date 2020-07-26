package com.umarabbas.assistme.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.umarabbas.assistme.R
import com.umarabbas.assistme.Utils
import com.umarabbas.assistme.models.ReminderTasks
import com.umarabbas.assistme.viewModels.ReminderTasksViewModel
import kotlinx.android.synthetic.main.reminder_tasks_item.view.*

class ReminderRVAdapter internal constructor(private val context : Context, private val viewModel : ReminderTasksViewModel): RecyclerView.Adapter<ReminderRVAdapter.ReminderRVViewHolder>() {

    private var tasks = emptyList<ReminderTasks>()
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    inner class ReminderRVViewHolder(itemView : View) :
        RecyclerView.ViewHolder(itemView){
        private var rvTitleTextView : TextView = itemView.findViewById(R.id.title_rv_item)
        private var rvDateTextView : TextView = itemView.findViewById(R.id.date_rv_item)


        fun bind(task : ReminderTasks){
            rvTitleTextView.text = task.title
            if(task.date.isNotEmpty()){
                val dateTime = "${task.date}, ${task.time}"
                rvDateTextView.text = dateTime
            }else{
                rvDateTextView.text = "You won't notified for quick tasks"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderRVViewHolder {
        val itemView = inflater.inflate(R.layout.reminder_tasks_item,parent,false)

        return ReminderRVViewHolder(itemView)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: ReminderRVViewHolder, position: Int) {
        val task : ReminderTasks = tasks[position]
        holder.bind(task)

        holder.itemView.rv_item_checkbox.setOnCheckedChangeListener{ btnView , isChecked ->
            if(isChecked){
                val builder = androidx.appcompat.app.AlertDialog.Builder(context)
                builder.setMessage("Are you sure you want to delete it?")
                builder.setPositiveButton("Yes"){dialogInterface, which ->
                    Utils.cancelAlarm(context, tasks[position].id)
                    viewModel.deleteTask(tasks[position])
                    holder.itemView.rv_item_checkbox.isChecked = false
//                Toast.makeText(holder.itemView.context,"Selected ${tasks[position].title}",Toast.LENGTH_SHORT).show()
                    notifyDataSetChanged()
                }
                builder.setNegativeButton("No"){dialogInterface, which ->
                    holder.itemView.rv_item_checkbox.isChecked = false
                }
                val alertDialog = builder.create()
                alertDialog.show()
            }
        }
        val parent = holder.itemView.card_view_main
        parent.setBackgroundColor(Color.WHITE)
    }
    fun setTasks(tasks : List<ReminderTasks>){
        this.tasks = tasks
        notifyDataSetChanged()
    }

}