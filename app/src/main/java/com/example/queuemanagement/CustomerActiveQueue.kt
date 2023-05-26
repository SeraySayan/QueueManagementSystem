package com.example.queuemanagement

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.queuemanagement.databinding.ActivityCustomerActiveQueueBinding
import com.google.firebase.firestore.ListenerRegistration


class CustomerActiveQueue : AppCompatActivity() {

    private lateinit var binding: ActivityCustomerActiveQueueBinding
    private val database = FirestoreDB()
    private var listenerRegistration: ListenerRegistration? = null
    private var leaveButtonPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerActiveQueueBinding.inflate(layoutInflater)
        setContentView(binding.root)
        leaveButtonPressed = false

        // Getting User uid and selected queue directory
        val uid = intent.getStringExtra("uid").toString()
        val selectedQueue = intent.getStringExtra("queue").toString()
        val priority = intent.getIntExtra("priority", 1)

        // Start listening for queue changes
        startListeningForQueueChanges(selectedQueue, uid, priority)

        binding.LeaveQueueButton.setOnClickListener {
            leaveButtonPressed = true
            stopListeningForQueueChanges()
            database.leaveQueue(selectedQueue, uid)
            Toast.makeText(this, "You left the queue", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun startListeningForQueueChanges(selectedQueue: String, uid: String, priority: Int) {
        listenerRegistration = database.getQueueActive(
            selectedQueue,
            uid,
            priority,
            listenerRegistration
        ) { tickets, waitTimes, ticketInQueue ->

            binding.queueNum.text = tickets[0].toString()

            var totalTime = 0
            for (x in waitTimes) {
                totalTime += x
            }
            binding.estRemaining.text = totalTime.toString()

            // Sending notification if position is 1 (before the top of the queue)
            if (tickets[0] == 1) {
                // TODO: Notification
            }

            // If ticketInQueue == false, that means there is no ticket
            // in the queue with the user uid. So, leave the queue screen
            if (!ticketInQueue) {
                // If the ticket dequeued by employee (no button press)
                // then navigate to the queue finished screen
                if (!leaveButtonPressed) {
                    // Sends the user to Queue Finished screen
                    Toast.makeText(this, "Queue is finished, your turn", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, CustomerQueueFinished::class.java)
                    intent.putExtra("uid", uid)
                    startActivity(intent)
                }
            }
        }
    }

    private fun stopListeningForQueueChanges() {
        listenerRegistration?.remove()
        listenerRegistration = null
    }

    override fun onDestroy() {
        super.onDestroy()
        stopListeningForQueueChanges()
    }
}
