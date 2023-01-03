package com.example.queuemanagement

import ClassFiles.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.queuemanagement.databinding.ActivityEmployeeListBinding
import com.google.firebase.firestore.FirebaseFirestore

class EmployeeList : AppCompatActivity() {
    lateinit var binding: ActivityEmployeeListBinding
    private lateinit var empList:ArrayList<Employee>

    private var db= FirebaseFirestore.getInstance() // TODO: implement custom class
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db= FirebaseFirestore.getInstance()

        binding.recyclerViewEmp.layoutManager = LinearLayoutManager(this)
        empList = arrayListOf()

        db.collection("Employees").get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    for (data in it.documents){
                        val emp:Employee? =data.toObject(Employee::class.java)
                        empList.add(emp!!)
                    }
                    val adapter = EmployeeAdapter(empList, this)
                    binding.recyclerViewEmp.adapter = adapter

                }
            }
            .addOnFailureListener{
                Toast.makeText(this,it.toString(), Toast.LENGTH_SHORT).show()
            }
    }
}