package com.umarabbas.assistme.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.umarabbas.assistme.R
import com.umarabbas.assistme.Utils
import com.umarabbas.assistme.models.AutoMessage
import com.umarabbas.assistme.viewModels.MessageViewModel
import kotlinx.android.synthetic.main.message_tasks_item.view.*
import kotlinx.android.synthetic.main.message_tasks_item.view.card_view_main
import kotlinx.android.synthetic.main.message_tasks_item.view.rv_item_checkbox
import kotlinx.android.synthetic.main.reminder_tasks_item.view.*

class MessageRVAdapter internal constructor(private val context : Context, private val viewModel : MessageViewModel): RecyclerView.Adapter<MessageRVAdapter.MessageRVViewHolder>() {

    private var tasks = emptyList<AutoMessage>()
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    inner class MessageRVViewHolder(itemView : View) :
        RecyclerView.ViewHolder(itemView){
        private var rvTitleTextView : TextView = itemView.findViewById(R.id.number_rv_item)
        private var rvMessageTextView : TextView = itemView.findViewById(R.id.message_rv_item)
        private var rvDateTextView : TextView = itemView.findViewById(R.id.date_rv_item)


        fun bind(task : AutoMessage){
            rvTitleTextView.text = task.number
            rvMessageTextView.text = task.msg
            val dateTime = "${task.date}, ${task.time}, ${task.nmbrOfMsg} messages"
            rvDateTextView.text = dateTime
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageRVViewHolder {
        val itemView = inflater.inflate(R.layout.message_tasks_item,parent,false)

        return MessageRVViewHolder(itemView)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: MessageRVViewHolder, position: Int) {
        val task : AutoMessage = tasks[position]
        holder.bind(task)

        holder.itemView.rv_item_checkbox.setOnCheckedChangeListener{ btnView , isChecked ->
            if(isChecked){
                val builder = androidx.appcompat.app.AlertDialog.Builder(context)
                builder.setMessage("Are you sure you want to delete it?")
                builder.setPositiveButton("Yes"){dialogInterface, which ->
                    Utils.cancelMessage(context, tasks[position].id)
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
    fun setTasks(tasks : List<AutoMessage>){
        this.tasks = tasks
        notifyDataSetChanged()
    }
}