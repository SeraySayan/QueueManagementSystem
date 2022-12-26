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

        val customerId = intent.getStringExtra("customer_id")
        //var queue = mutableListOf<Any>()
        var queue = ArrayList<Ticket>()

        database.listenToChanges("/Queue/queue1/TicketsInQueue") { querySnapshot ->
            database.getQueue2("/Queue/queue1/TicketsInQueue",customerId!!) { tickets ->
               // queue = tickets
            }
        }

                // setting text
                binding.queueNum2.setText(queue[0].id)

                binding.remainingTime2.setText(queue[0].total_waited_time)

                binding.LeaveQueueButton.setOnClickListener {

                    // TODO: implement leave queue
                   queue[0].status= false
                    queue.remove(queue[0])

                    val intent = Intent(this, CustomerMenuActivity::class.java)
                    startActivity(intent)
                        }
                }

            }





