package com.example.queuemanagement

import ClassFiles.Ticket
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QueueAdapter(private val queueList: MutableList<Ticket>, //val user_uid:String //probably no need
                   private val context: Context): RecyclerView.Adapter<QueueAdapter.QueueViewHolder>() {

    class QueueViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val tvName: TextView = itemView.findViewById(R.id.textView1)
        val tvSurname: TextView = itemView.findViewById(R.id.textView2)
        val tvpType: TextView =itemView.findViewById(R.id.textView3)

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueueViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item4,parent,false)
        return QueueViewHolder(itemView)
    }
//TODO:name surname çek
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: QueueViewHolder, position: Int) {
        holder.tvName.text = "Name: ${queueList[position].name}"
        holder.tvSurname.text = "Surname: ${queueList[position].surname}"
        holder.tvpType.text = "Process Type: ${queueList[position].processType}"

    }

    override fun getItemCount(): Int {
        return queueList.size
    }


}