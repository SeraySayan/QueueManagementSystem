package com.example.queuemanagement

import ClassFiles.*
import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.queuemanagement.databinding.ActivityCustomerTransactionBinding


class CustomerTransaction : AppCompatActivity() {
    public var processTime =0
    public var processType=" "

    var database = FirestoreDB()
    lateinit var binding: ActivityCustomerTransactionBinding
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var c2 = Customer(1223,"Aslı","Yıldırım",
            "aslı.yıldırım@metu.edu.tr","654321",
            "25/12/2022","+905345678","İstanbul")


       // var queue = mutableListOf<Any>()
        var queue = ArrayList<Ticket>()

        database.listenToChanges("/Queue/queue1/TicketsInQueue") { querySnapshot ->

            database.getQueue("/Queue/queue1/TicketsInQueue") { tickets ->

               // queue = tickets

            }
        }
        val count= queue.count()
        var totalTime = 0
        binding.WorDButton.setOnClickListener {

            processTime= 20
            processType="Withdraw/Deposit Money"
            totalTime= queue[queue.size-1].total_waited_time + processTime

            binding.peopleCount.setText(count.toString())
            binding.remainingTime.setText(totalTime.toString())

        }

        binding.paymentButton.setOnClickListener {

            processTime= 25
            processType="Payments"
            totalTime= queue[queue.size-1].total_waited_time + processTime
            binding.peopleCount.setText(count.toString())
            binding.remainingTime.setText(totalTime.toString())
        }

        binding.currencyButton.setOnClickListener {

            processTime= 30
            processType="Investment to Currency"
            totalTime= queue[queue.size-1].total_waited_time + processTime
            binding.peopleCount.setText(count.toString())
            binding.remainingTime.setText(totalTime.toString())
        }

        binding.newAccountButton.setOnClickListener {

            processTime= 40
            processType="Open New Account"
            totalTime= queue[queue.size-1].total_waited_time + processTime
            binding.peopleCount.setText(count.toString())
            binding.remainingTime.setText(totalTime.toString())

        }


        binding.EnterButton.setOnClickListener{

            database.addData("/Queue/queue1/TicketsInQueue",Ticket( queue[queue.size-1].id+1,0, processType,totalTime,c2.id))
            val intent = Intent(this, CustomerActiveQueue::class.java)
            startActivity(intent)
            intent.putExtra("customer_id",c2.id)
        }



    }




}