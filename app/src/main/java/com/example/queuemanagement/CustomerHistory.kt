package com.example.queuemanagement

import ClassFiles.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.queuemanagement.databinding.ActivityCustomerListBinding
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.ticker

class CustomerHistory : AppCompatActivity() {
    lateinit var binding: ActivityCustomerListBinding
    private lateinit var ticketList:ArrayList<Ticket>



    private var db= FirebaseFirestore.getInstance() // TODO: implement custom class
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var user_uid = intent.getStringExtra("uid").toString()

        db= FirebaseFirestore.getInstance()

        binding.recyclerViewCus.layoutManager = LinearLayoutManager(this)
        ticketList = arrayListOf()

        db.collection("Tickets").whereEqualTo("customer_id",user_uid).get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    for (data in it.documents){
                        val ticket:Ticket? =data.toObject(Ticket::class.java)
                        ticketList.add(ticket!!)
                    }
                    val adapter = CustomerTicketHistoryAdapter(ticketList, this)
                    binding.recyclerViewCus.adapter = adapter

                }
            }
            .addOnFailureListener{
                Toast.makeText(this,it.toString(), Toast.LENGTH_SHORT).show()
            }
    }
}