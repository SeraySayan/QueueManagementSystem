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

class EmployeeQueue : AppCompatActivity() {
    lateinit var binding: ActivityEmployeeQueueBinding
    private lateinit var queueList:ArrayList<Ticket>
    val database = FirestoreDB()

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
                if(!queueList.isEmpty()){
                    //TODO:if there is no one, add the first one; if there is, wait
                    binding.textView1.text = "Name: "+queueList[0].name
                    binding.textView2.text = "Surname: "+queueList[0].surname
                    binding.textView3.text = "Process Type: "+queueList[0].processType
                }
                // If queue is empty
                else{
                    binding.textView1.text = "Name: "
                    binding.textView2.text = "Surname: "
                    binding.textView3.text = "Process Type: "
                }
                // Update the adapter with other values
                val adapter = QueueAdapter(queueList, this)
                binding.recyclerView.adapter = adapter
            }
        }

        // Dequeue buttons
        binding.button1.setOnClickListener {
            database.dequeue(eQueue)
            Toast.makeText(this, "Process completed!", Toast.LENGTH_SHORT).show()
        }
        binding.button2.setOnClickListener {
            database.dequeue(eQueue)
            Toast.makeText(this, "Process cancelled!", Toast.LENGTH_SHORT).show()
        }

    }
}


