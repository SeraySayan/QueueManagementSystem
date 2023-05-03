package com.example.queuemanagement

import ClassFiles.Ticket
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.queuemanagement.databinding.ActivityEmployeeQueueBinding


/**
 * 26.03.2023
 * In order to achieve multiple employee,
 * we must change the code structure.
 * Until now, there is no "serving" mechanics.
 * It just fetches the queue, and assumes that
 * the first ticket of the queue is the customer
 * that needs to be served. This logic is wrong
 * since if we going to work with multiple employees,
 * the employee first reserve the customer that s/he
 * is serving.
 *
 * So, a "serve" mechanic must be implemented. Employee
 * will see the queue, and when s/he pressed the button
 * "serve", s/he will perform a dequeue operation to the
 * first customer. That means, when a customer is on
 * serving process, s/he will not be in the queue.
 *
 * As an employee: first dequeue, then serve the customer
 * This will prevent the clashes between multiple employees.
 *
 *
 */


class EmployeeQueue : AppCompatActivity() {

    lateinit var binding: ActivityEmployeeQueueBinding  // Binding for UI buttons
    val database = FirestoreDB()                        // Database initialization
    var serving = 0                                     // Serving status for the algorithm
    lateinit var servingTicket: Ticket                  // Active Ticket object that is serving
    var user_uid = ""                                   // Employee's uid for database query


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeQueueBinding.inflate(layoutInflater)
        setContentView(binding.root)
        user_uid = intent.getStringExtra("uid").toString()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)



        database.getDocumentByField("Employees","uid",user_uid){ data ->

            val branchMap = data?.get("branch") as Map<*, *>
            val eQueue = branchMap["Queue"] as String

            database.listenToChanges(eQueue){ querySnapshot ->

                var queueList = mutableListOf<Ticket>()
                database.getQueueTEST(eQueue) { data ->

                    // data is the ticket list of queue
                    for (x in data){
                        queueList.add(x)
                    }

                    // If there is at least one ticket in queue
                    if(serving == 1){
                        binding.textView1.text = "Name: "+ servingTicket.name
                        binding.textView2.text = "Surname: "+ servingTicket.surname
                        binding.textView3.text = "Process Type: "+ servingTicket.processType
                    }
                    // If queue is empty
                    else{
                        resetOutput()
                    }
                    // Update the adapter with other values
                    val adapter = QueueAdapter(queueList, this)
                    binding.recyclerView.adapter = adapter
                }
            }

            // Dequeue buttons
            binding.button1.setOnClickListener {

                if(serving==1){
                    resetOutput()
                    processTicket(servingTicket, 1)
                    Toast.makeText(this, "Process completed!", Toast.LENGTH_SHORT).show()
                }
                serving = 0
            }
            binding.button2.setOnClickListener {

                if(serving==1){
                    resetOutput()
                    processTicket(servingTicket, 0)
                    Toast.makeText(this, "Process cancelled!", Toast.LENGTH_SHORT).show()
                }

                serving = 0
            }

            // Next Customer button
            binding.button7.setOnClickListener{

                // Listen to the queue. If its empty, do nothing.
                database.getQueueTEST(eQueue){ data->


                    // In order to call next customer, queue must be non-empty
                    // and the employee must be free (without any serving)
                    if ((data.size > 0) && serving == 0){

                        serving = 1
                        database.getNextCustomer(eQueue){targetTicket ->
                            servingTicket = targetTicket[0]
                            servingTicket.updateExitTime()
                            println(servingTicket.name + " " + servingTicket.processType)
                            database.dequeue(eQueue)
                        }
                    }
                }

            }



        }








    }

    @SuppressLint("SetTextI18n")
    // It resets the info area
    fun resetOutput(){
        binding.textView1.text = "Name: "
        binding.textView2.text = "Surname: "
        binding.textView3.text = "Process Type: "
    }

    // Performs final processes to the serving ticket,
    // then saves the ticket to the collection.
    fun processTicket(ticket: Ticket, status: Int){

        if(status==1){
            ticket.result = "Completed"
        }
        else{
            ticket.result = "Cancelled"
        }
        database.getDocumentByField("Employees", "uid", user_uid){data->



            val emp_name = data?.get("name").toString() + " " + data?.get("surname").toString()
            ticket.served_employee = emp_name
            ticket.updateEndServeTime()
            ticket.calculateProcessTime()
            ticket.calculateWaitTime()
            database.addData("Tickets", ticket)

        }


    }

}


