package com.example.queuemanagement

import ClassFiles.Customer
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomerAdapter(private val customerList:ArrayList<Customer>, //val user_uid:String //probably no need
                      private val context: Context): RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>() {

    class CustomerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val tvName: TextView = itemView.findViewById(R.id.textView1)
        val tvSurname: TextView = itemView.findViewById(R.id.textView2)
        val tvCustomerID: TextView =itemView.findViewById(R.id.textView3)
        val tvButton: TextView = itemView.findViewById(R.id.button)
        init {
            tvButton.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item2,parent,false)
        return CustomerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        holder.tvName.text = "Name: ${customerList[position].name}"
        holder.tvSurname.text = "Surname: ${customerList[position].surname}"
        holder.tvCustomerID.text = "UID: ${customerList[position].uid}"
        holder.tvButton.setOnClickListener {
            Log.d("ItemClick", "Item at position $position clicked")
            val intent = Intent(context, CustomerDetails::class.java )
            intent.putExtra("uid", customerList[position].uid)
            intent.putExtra("name", customerList[position].name)
            intent.putExtra("surname", customerList[position].surname)
            intent.putExtra("email", customerList[position].email)
            intent.putExtra("reg_date", customerList[position].reg_date)
            intent.putExtra("priority", customerList[position].priority)
            context.startActivity(intent)


        }
        // holder.tvID.text =branchlist[position].id.toString()
    }

    override fun getItemCount(): Int {
        return customerList.size
    }


}