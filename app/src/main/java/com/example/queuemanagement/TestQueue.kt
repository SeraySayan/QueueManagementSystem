package com.example.queuemanagement

import ClassFiles.Ticket
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.queuemanagement.databinding.ActivityTestqueueBinding
import com.google.firebase.firestore.FieldValue

class TestQueue : AppCompatActivity(){

    private lateinit var binding: ActivityTestqueueBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestqueueBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = FirestoreDB()

        binding.buttonAddTicket.setOnClickListener{
            println("ADD TICKET TUŞUNA BASTIN")

            val process = binding.editTextProcess.text.toString()
            val ticketNo = binding.editTextTicketno.text.toString().toInt()
            val priority = binding.editTextPriority.text.toString().toInt()
            var time = FieldValue.serverTimestamp()
            database.addData("/Queue/queue1/TicketsInQueue",Ticket(ticketNo,priority,process,1))

            var myList = mutableListOf<Any>()

            database.listenToChanges("/Queue/queue1/TicketsInQueue") { querySnapshot ->

                database.getQueue("/Queue/queue1/TicketsInQueue") { data ->

                    myList = data
                    binding.textView3.setText(myList.toString())



                }
            }
        }
        binding.buttonDequeue.setOnClickListener{
            println("DEQUEUE TUŞUNA BASTIN")
                database.dequeue("/Queue/queue1/TicketsInQueue")

        }





        //TODO: ADD TICKET ÇALIŞIYOR, DEQUEUE TUŞUNU YAP        DONE!
        //TODO: TIMESTAMP İŞİNİ YAPB                            DONE!
        //TODO: FORMAT DB VALUES
        //TODO: USE PROPER VIEW FOR LIST






    }
}

