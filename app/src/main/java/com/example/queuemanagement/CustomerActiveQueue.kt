package com.example.queuemanagement

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.queuemanagement.databinding.ActivityCustomerActiveQueueBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration


class CustomerActiveQueue : AppCompatActivity() {

    private lateinit var binding: ActivityCustomerActiveQueueBinding
    private val database = FirestoreDB()
    private var listenerRegistration: ListenerRegistration? = null

    // Keeping the leaveButtonPressed shared between db listener and button listener
    companion object{
         var leaveButtonPressed: Boolean = false
    }



    // Dynamic priority delay things
    private lateinit var handler: Handler
    private lateinit var updateRunnable: Runnable



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerActiveQueueBinding.inflate(layoutInflater)
        setContentView(binding.root)
        leaveButtonPressed = false

        // Getting User uid and selected queue directory
        val uid = intent.getStringExtra("uid").toString()
        val selectedQueue = intent.getStringExtra("queue").toString()
        val branch_name = intent.getStringExtra("branch_name").toString()
        val priority = intent.getIntExtra("priority", 1)

        binding.textViewBranchName.text = branch_name


        // Start listening for queue changes
        startListeningForQueueChanges(selectedQueue, uid, priority)

        binding.button5.setOnClickListener {
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
            val channelDescription = "This will be sent by the position"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }

        // Fetch the delay time from Firestore and schedule the update
        fetchDelayTimeAndScheduleUpdate(selectedQueue, uid, priority.toInt())

    }

    private fun startListeningForQueueChanges(selectedQueue: String, uid: String, priority: Int) {
        listenerRegistration = database.getQueueActive(
            selectedQueue,
            uid,
            priority,
            listenerRegistration
        ) { tickets, waitTimes, ticketInQueue ->

            binding.textViewPosition.text = tickets[0].toString()

            var totalTime = 0
            for (x in waitTimes) {
                totalTime += x
            }
            binding.textViewWaitTime.text = "$totalTime min"


            // Sending notification if position is 2 (before the top of the queue)
            if (tickets[0] == 2) {
                sendNotification("Get Ready", "You are on position 2. Get ready for service")
            }
            // Sending notification if position is 1 (top of the queue)
            if (tickets[0] == 1) {
                sendNotification("Your Turn", "It's your turn on the queue")
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

    private fun sendNotification(title: String, text: String) {
        val channelId = "your_turn_notification"
        val notificationId = 1

        // Create an explicit intent for the current Activity in your app
        val intent = Intent(this, CustomerActiveQueue::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)

        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.queue_mamangement_icon_v2)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(notificationId, builder.build())
    }

    private fun fetchDelayTimeAndScheduleUpdate(selectedQueue: String, uid: String, priority: Int) {
        // Get a reference to the Firestore collection and document

        val db = FirebaseFirestore.getInstance()
        val queueDocRef = db.collection("DynamicPriority").document("threshold")

        // Fetch the document data
        queueDocRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // Retrieve the delay time from the document
                    val delayTime = documentSnapshot.getLong("seconds")

                    if (delayTime != null) {
                        // Schedule the update after the delay time
                        handler = Handler()
                        updateRunnable = Runnable {
                            // Perform the update operation
                            database.updateDynamicPriority(selectedQueue, uid, priority)

                        }
                        handler.postDelayed(updateRunnable, delayTime*1000)
                    }
                }
            }
            .addOnFailureListener { e ->
                // Handle the failure to fetch the document data
            }
    }

    private fun updateDocumentAfterDelay(selectedQueue: String, uid: String) {
        // ...
    }




    override fun onDestroy() {
        super.onDestroy()
        stopListeningForQueueChanges()
        handler.removeCallbacks(updateRunnable)
    }
}
