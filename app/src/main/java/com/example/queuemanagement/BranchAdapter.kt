package com.example.queuemanagement

import ClassFiles.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BranchAdapter(private val branchlist:ArrayList<Branchs>): RecyclerView.Adapter<BranchAdapter.BranchViewHolder>() {
    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    class BranchViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvName: TextView =itemView.findViewById(R.id.textView)
        val tvLocation: TextView = itemView.findViewById(R.id.textView2)
        val tvQueueList: TextView = itemView.findViewById(R.id.textView3)
        //val tvID: TextView = itemView.findViewById(R.id.textView4)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return BranchViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BranchViewHolder, position: Int) {
        holder.tvName.text = branchlist[position].name
        holder.tvLocation.text = branchlist[position].location.toString()
        holder.tvQueueList.text ="Waiting Customers "+branchlist[position].Queue

        holder.itemView.setOnClickListener {
            listener?.onItemClick(position)
        }
       // holder.tvID.text =branchlist[position].id.toString()
    }

    override fun getItemCount(): Int {
        return branchlist.size
    }
}


