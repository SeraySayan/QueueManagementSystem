package com.example.queuemanagement

import ClassFiles.*
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BranchAdapter(private val branchlist:ArrayList<Branch>, val user_uid:String, private val context: Context): RecyclerView.Adapter<BranchAdapter.BranchViewHolder>() {


    val database = FirestoreDB()

    class BranchViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),View.OnClickListener {
        val tvName: TextView =itemView.findViewById(R.id.textView)
        val tvLocation: TextView = itemView.findViewById(R.id.textView2)
        val tvQueueList: TextView = itemView.findViewById(R.id.textView3)
        val tvEstTime: TextView = itemView.findViewById(R.id.textView4)

        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            println("zaaaaaaaaaaaaaaaaaaaaa\n")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return BranchViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BranchViewHolder, position: Int) {

        // Getting the current queue information per queue with this async function
        database.getTransactionQueue(branchlist[position].Queue!!,0){queue_size, wait_times->
            val queue_size =  queue_size.size  // getting the queue size
            var est_wait_time = 0
            for (x in wait_times) {
                est_wait_time += x
            }
            holder.tvName.text = branchlist[position].name
            holder.tvLocation.text = branchlist[position].distance?.toInt().toString() + " meters away"
            holder.tvQueueList.text ="Waiting Customers: $queue_size "     // TODO: FIX HERE
            holder.tvEstTime.text = "Estimated Time: $est_wait_time"

            holder.itemView.setOnClickListener{
                println("bbbbbbbbbbbbbbbbbbbbbbbb\n")
                Log.d("ItemClick", "Item at position $position clicked")
                val intent = Intent(context, CustomerTransaction::class.java )
                intent.putExtra("uid", user_uid)
                intent.putExtra("branch_name",branchlist[position].name)
                intent.putExtra("queue_location", branchlist[position].Queue)
                context.startActivity(intent)
            }





        }
       // holder.tvID.text =branchlist[position].id.toString()
    }

    override fun getItemCount(): Int {
        return branchlist.size
    }


}