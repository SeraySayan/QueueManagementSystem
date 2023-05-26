package com.example.queuemanagement

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.queuemanagement.databinding.ActivityCustomerActiveQueueBinding


class CustomerActiveQueue : AppCompatActivity() {

    private lateinit var binding: ActivityCustomerActiveQueueBinding
    val database = FirestoreDB()
    var leaveButtonPressed = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerActiveQueueBinding.inflate(layoutInflater)
        setContentView(binding.root)
        leaveButtonPressed = false


        // Getting User uid and selected queue directory
        var uid = intent.getStringExtra("uid").toString()
        var selected_queue = intent.getStringExtra("queue").toString()
        var priority = intent.getIntExtra("priority",1)

        //it only takes the ones whose priorities are bigger or equal to the this ticket and wait_times
        database.listenToChanges(selected_queue) { querySnapshot ->

            database.getQueueActive(selected_queue, uid, priority) { tickets, wait_times, ticketInQueue ->

                binding.queueNum.setText(tickets[0].toString())

                var total_time = 0
                for (x in wait_times) {
                    total_time += x
                }
                binding.estRemaining.setText(total_time.toString())


                //Sending notification if position is 1 (before the top of the queue)
                if(tickets[0] == 1){
                    //TODO: Notificaiton
                }

                // If ticketInQueue == false, that means there is no ticket
                // in the queue with the user uid. So, leave the queue screen
                if(!ticketInQueue){

                    // If the ticket dequeued by employee (no button press)
                    // then navigate to the queue finished screen
                    if(!leaveButtonPressed){
                        // Sends the user to Queue Finished screen
                        Toast.makeText(this, "Queue is finished, your turn", Toast.LENGTH_SHORT).show()
                        intent = Intent(this, CustomerQueueFinished::class.java)
                        intent.putExtra("uid",uid)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                    }
                    // If user pressed the leave queue button, navigate back
                    else{
                        Toast.makeText(this, "Your leaved the queue", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }

        binding.LeaveQueueButton.setOnClickListener {
            leaveButtonPressed = true
            database.leaveQueue(selected_queue,uid)
        }

    }

}
