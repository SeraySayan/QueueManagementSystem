package com.example.queuemanagement

import ClassFiles.Ticket
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.queuemanagement.databinding.ActivityEmployeeQueueBinding
import com.google.common.base.Stopwatch
import com.google.firebase.firestore.FirebaseFirestore

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
    lateinit var binding: ActivityEmployeeQueueBinding
    private lateinit var queueList:ArrayList<Ticket>
    val database = FirestoreDB()
    var serving = 0
    var servingTicket = Ticket()


    fun resetOutput(){
        binding.textView1.text = "Name: "
        binding.textView2.text = "Surname: "
        binding.textView3.text = "Process Type: "
    }


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeQueueBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val eQueue="/Queue/queue1/TicketsInQueue" //TODO: databaseden hangi queue olduğunu çek

        binding.recyclerView.layoutManager = LinearLayoutManager(this)


        database.listenToChanges(eQueue){ querySnapshot ->

            var queueList = mutableListOf<Ticket>()
            database.getQueueTEST(eQueue) { data ->

                // data is the ticket list of queue
                for (x in data){
                    queueList.add(x)
                }

                // If there is at least one ticket in queue
                if(serving == 1){
                    //TODO:if there is no one, add the first one; if there is, wait
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
            serving = 0
            resetOutput()
            Toast.makeText(this, "Process completed!", Toast.LENGTH_SHORT).show()
        }
        binding.button2.setOnClickListener {
            serving = 0
            resetOutput()
            Toast.makeText(this, "Process cancelled!", Toast.LENGTH_SHORT).show()
        }

        // Next Customer button
        binding.button7.setOnClickListener{

            // Listen to the queue. If its empty, do nothing.
            database.getQueueTEST(eQueue){ data->

                if (data.size > 0){

                    serving = 1
                    database.getNextCustomer(eQueue){targetTicket ->
                        servingTicket = targetTicket[0]
                        println(servingTicket.name + " " + servingTicket.processType)
                        database.dequeue(eQueue)
                    }
                }
            }

        }

    }
}


