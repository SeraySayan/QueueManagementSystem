package com.example.queuemanagement

import ClassFiles.*
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class BranchAdapter(private val branchlist:ArrayList<Branchs>, val user_uid:String, private val context: Context): RecyclerView.Adapter<BranchAdapter.BranchViewHolder>() {

    class BranchViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),View.OnClickListener {
        val tvName: TextView =itemView.findViewById(R.id.textView)
        val tvLocation: TextView = itemView.findViewById(R.id.textView2)
        val tvQueueList: TextView = itemView.findViewById(R.id.textView3)
        val tvButton: TextView = itemView.findViewById(R.id.button4)
        init {
            tvButton.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return BranchViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BranchViewHolder, position: Int) {
        holder.tvName.text = branchlist[position].name
        holder.tvLocation.text = branchlist[position].location.toString()
        holder.tvQueueList.text ="Waiting Customers:  "     // TODO: FIX HERE
        holder.tvButton.setOnClickListener {
            Log.d("ItemClick", "Item at position $position clicked")
            val intent = Intent(context, CustomerTransaction::class.java )
            intent.putExtra("uid", user_uid)
            intent.putExtra("queue_location", branchlist[position].Queue)
            context.startActivity(intent)


        }
       // holder.tvID.text =branchlist[position].id.toString()
    }

    override fun getItemCount(): Int {
        return branchlist.size
    }


}