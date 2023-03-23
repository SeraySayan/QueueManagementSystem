package com.example.queuemanagement

import ClassFiles.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.queuemanagement.databinding.ActivityCustomerListBinding
import com.google.firebase.firestore.FirebaseFirestore

class CustomerList : AppCompatActivity() {
    lateinit var binding: ActivityCustomerListBinding
    private lateinit var customerList:ArrayList<Customer>

    private var db= FirebaseFirestore.getInstance() // TODO: implement custom class
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db= FirebaseFirestore.getInstance()

        binding.recyclerViewCus.layoutManager = LinearLayoutManager(this)
        customerList = arrayListOf()

        db.collection("Customers").get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    for (data in it.documents){
                        val customer:Customer? =data.toObject(Customer::class.java)
                        customerList.add(customer!!)
                    }
                    val adapter = CustomerAdapter(customerList, this)
                    binding.recyclerViewCus.adapter = adapter

                }
            }
            .addOnFailureListener{
                Toast.makeText(this,it.toString(), Toast.LENGTH_SHORT).show()
            }
    }
}