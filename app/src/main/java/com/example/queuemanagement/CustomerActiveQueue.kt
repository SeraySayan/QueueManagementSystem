package com.example.queuemanagement

import ClassFiles.*
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.queuemanagement.databinding.ActivityCustomerActiveQueueBinding
import com.google.firebase.*
import com.google.firebase.firestore.*



class CustomerActiveQueue : AppCompatActivity() {

    private lateinit var binding: ActivityCustomerActiveQueueBinding
    val database = FirestoreDB()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerActiveQueueBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Getting User uid and selected queue directory
        var uid = intent.getStringExtra("uid").toString()
        var selected_queue = intent.getStringExtra("queue").toString()


        database.listenToChanges(selected_queue) { querySnapshot ->

            database.getQueueActive(selected_queue, uid) { tickets, wait_times ->

                binding.queueNum.setText(tickets[0].toString())

                var total_time = 0
                for (x in wait_times){
                    total_time += x
                }

                binding.estRemaining.setText(total_time.toString())

            }
        }


        //TODO: Implement LeaveQueue
        binding.LeaveQueueButton.setOnClickListener {
            database.leaveQueue(selected_queue,uid)
            finish()

        }
    }

}





