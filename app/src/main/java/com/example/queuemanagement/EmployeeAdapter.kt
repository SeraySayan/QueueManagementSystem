package com.example.queuemanagement

import ClassFiles.Employee
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EmployeeAdapter(private val empList:ArrayList<Employee>, //val user_uid:String //probably no need
    private val context: Context): RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {

        class EmployeeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
            val tvName: TextView = itemView.findViewById(R.id.textView1)
            val tvSurname: TextView = itemView.findViewById(R.id.textView2)
            val tvEmpID: TextView =itemView.findViewById(R.id.textView3)
            val tvButton: TextView = itemView.findViewById(R.id.button)
            init {
                tvButton.setOnClickListener(this)
            }
            override fun onClick(v: View?) {
                TODO("Not yet implemented")
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item3,parent,false)
            return EmployeeViewHolder(itemView)
        }


        override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
            holder.tvName.text = "Name: ${empList[position].name}"
            holder.tvSurname.text = "Surname: ${empList[position].surname}"
            holder.tvEmpID.text = "UID: ${empList[position].uid}"
            holder.tvButton.setOnClickListener {
                Log.d("ItemClick", "Item at position $position clicked")
                val intent = Intent(context, EmployeeDetails::class.java )
                intent.putExtra("uid", empList[position].uid)
                intent.putExtra("name", empList[position].name)
                intent.putExtra("surname", empList[position].surname)
                intent.putExtra("email", empList[position].email)
                intent.putExtra("reg_date", empList[position].reg_date)
                intent.putExtra("branch", empList[position].branch.name)
                context.startActivity(intent)


            }
            // holder.tvID.text =branchlist[position].id.toString()
        }

        override fun getItemCount(): Int {
            return empList.size
        }



}