package com.example.queuemanagement

import ClassFiles.Ticket
import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.example.queuemanagement.databinding.ActivityTestqueueBinding
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.QuerySnapshot

class TestQueue : AppCompatActivity(){

    private lateinit var binding: ActivityTestqueueBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestqueueBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val database = FirestoreDB()

        var processType = ""
        // Dropdown menu
        val elements= arrayOf("Withdraw/Deposit Money","Payments","Investment to Currency","Open New Account")
        val adapter= ArrayAdapter(this, R.layout.simple_spinner_item,elements)
        binding.spinner2.setAdapter(adapter)
        binding.spinner2.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener{
            @SuppressLint("SetTextI18n")
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(position==0){
                    processType="Withdraw/Deposit Money"
                }
                else if(position==1){
                    processType="Payments"
                }
                else if(position==2){
                    processType="Investment to Currency"
                }
                else if(position==3){
                    processType="Open New Account"
                }
            }
            // ?
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

        // Switch for different queues
        var selected_queue = "/Queue/queue1/TicketsInQueue" //Default value
        binding.switch1.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {
                selected_queue = "/Queue/queue2/TicketsInQueue"
            } else {
                selected_queue = "/Queue/queue1/TicketsInQueue"
            }

            // calling getQueue as well to dynamic update with switch changes
            database.listenToChanges(selected_queue){ querySnapshot ->
                var myList = mutableListOf<Ticket>()
                database.getQueueTEST(selected_queue) { data ->
                    myList = data
                    var tempList = mutableListOf<String>()
                    for (x in myList){
                        tempList.add( x.id.toString() + " " + x.processType + " " + x.priority + "\n"   )
                    }
                    binding.textView3.setText(tempList.toString())
                }
            }
        }

        // Listening to queues and fetching it in real time
        var myList = mutableListOf<Ticket>()
        database.listenToChanges(selected_queue){ querySnapshot ->
            database.getQueueTEST(selected_queue) { data ->

                myList = data
                var tempList = mutableListOf<String>()

                for (x in myList){
                    tempList.add( x.id.toString() + " " + x.processType + " " + x.priority + "\n"   )
                }

                binding.textView3.setText(tempList.toString())

            }

        }

        binding.buttonAddTicket.setOnClickListener{
            println("ADD TICKET TU??UNA BASTIN")

            val ticketNo = binding.editTextTicketno.text.toString().toInt()
            val priority = binding.editTextPriority.text.toString().toInt()
            database.addData(selected_queue,Ticket(ticketNo,priority,processType,"1"))


        }
        binding.buttonDequeue.setOnClickListener{
            println("DEQUEUE TU??UNA BASTIN")
                database.dequeue(selected_queue)

        }

        //TODO: ADD TICKET ??ALI??IYOR, DEQUEUE TU??UNU YAP        DONE!
        //TODO: TIMESTAMP ??????N?? YAPB                            DONE!
        //TODO: FORMAT DB VALUES
        //TODO: USE PROPER VIEW FOR LIST






    }
}

