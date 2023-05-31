package com.example.queuemanagement

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.queuemanagement.databinding.ActivityCustomerActiveQueueBinding
import com.google.firebase.firestore.ListenerRegistration


class CustomerActiveQueue : AppCompatActivity() {

    private lateinit var binding: ActivityCustomerActiveQueueBinding
    private val database = FirestoreDB()
    private var listenerRegistration: ListenerRegistration? = null

    // Keeping the leaveButtonPressed shared between db listener and button listener
    companion object{
         var leaveButtonPressed: Boolean = false
    }

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

        // Creating the notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "your_turn_notification"
            val channelName = "Queue Your Turn Notification"
            val channelDescription = "This will be sent when the user on position 1"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
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
                sendNotification()
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

    private fun sendNotification() {
        val channelId = "your_turn_notification"
        val notificationId = 1

        val builder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.queue_mamangement_icon)
            .setContentTitle("Your Turn")
            .setContentText("You are the next customer to service on the qeuue")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(notificationId, builder.build())
    }


    override fun onDestroy() {
        super.onDestroy()
        stopListeningForQueueChanges()
    }
}
