package com.umarabbas.assistme.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.umarabbas.assistme.R
import com.umarabbas.assistme.Utils
import com.umarabbas.assistme.models.AutoLock
import com.umarabbas.assistme.viewModels.LockViewModel
import kotlinx.android.synthetic.main.reminder_tasks_item.view.*

class LockRVAdapter internal constructor(private val context : Context, private val viewModel : LockViewModel): RecyclerView.Adapter<LockRVAdapter.LockRVViewHolder>() {

    private var tasks = emptyList<AutoLock>()
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    inner class LockRVViewHolder(itemView : View) :
        RecyclerView.ViewHolder(itemView){
        private var rvTitleTextView : TextView = itemView.findViewById(R.id.title_rv_item)
        private var rvDateTextView : TextView = itemView.findViewById(R.id.date_rv_item)


        fun bind(task : AutoLock){
            rvTitleTextView.text = "Lock"
            val dateTime = "${task.date}, ${task.time}"
            rvDateTextView.text = dateTime
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LockRVAdapter.LockRVViewHolder {
        val itemView = inflater.inflate(R.layout.reminder_tasks_item,parent,false)
        return LockRVViewHolder(itemView)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: LockRVAdapter.LockRVViewHolder, position: Int) {
        val task : AutoLock = tasks[position]
        holder.bind(task)

        holder.itemView.rv_item_checkbox.setOnCheckedChangeListener{ btnView , isChecked ->
            if(isChecked){
                val builder = androidx.appcompat.app.AlertDialog.Builder(context)
                builder.setMessage("Are you sure you want to delete it?")
                builder.setPositiveButton("Yes"){dialogInterface, which ->
                    Utils.cancelLock(context, tasks[position].id)
                    viewModel.deleteTask(tasks[position])
                    holder.itemView.rv_item_checkbox.isChecked = false
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

    fun setTasks(tasks : List<AutoLock>){
        this.tasks = tasks
        notifyDataSetChanged()
    }
}