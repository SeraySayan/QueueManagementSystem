package com.example.myapplication
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BranchAdapter(private val branchlist:ArrayList<Branchs>): RecyclerView.Adapter<BranchAdapter.BranchViewHolder>() {
    class BranchViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvName: TextView =itemView.findViewById(R.id.textView)
        val tvLocation: TextView = itemView.findViewById(R.id.textView2)
        val tvCustomerList: TextView = itemView.findViewById(R.id.textView3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return BranchViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BranchViewHolder, position: Int) {
        holder.tvName.text = branchlist[position].Name
        holder.tvLocation.text = branchlist[position].Location.toString()
        holder.tvCustomerList.text =branchlist[position].CustomerList.toString()

    }

    override fun getItemCount(): Int {
        return branchlist.size
    }
}