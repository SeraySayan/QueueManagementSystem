package com.example.queuemanagement

import ClassFiles.*
import android.R
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.queuemanagement.databinding.ActivityCustomerTransactionBinding

//TODO: processType seçim UI daha iyi olabilir. Bir de bu sayfayı branchList sonrasında çağıracaz
class CustomerTransaction : AppCompatActivity() {
    public var processTime =0
    public var processType=" "

    var database = FirestoreDB()
    lateinit var binding: ActivityCustomerTransactionBinding


    // Hashmap for keeping the processType names and their estimated time.
    var map: HashMap<String, Int> = hashMapOf("Withdraw/Deposit Money" to 20,
                                              "Payments" to 25,
                                              "Investment to Currency" to 30,
                                              "Open New Account" to 40)

    // also, hashmap can be updated dynamically with built in methods.


    @SuppressLint("SetTextI18n") // Supressing a weird warning (NOT IMPORTANT, JUST KEEP IT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // IN ACTUAL CASE, we will come here after the branch selection,
        // we got the info that which branch is selected. but for now,
        // just initialize a random queue
        var selected_queue = "/Queue/queue1/TicketsInQueue"


        database.listenToChanges(selected_queue) { querySnapshot ->

            //TODO: TEST versiyonunda hata var. normal hali çalışıyor. queue size çalışıyor, timing için class yapmayı dene
            database.getQueue(selected_queue) { tickets ->

                var queue_size =  tickets.size  // getting the queue size
                binding.peopleCount.setText("There are $queue_size customers in the line")

                var est_wait_time = 0
                for (ticket in tickets){
                 //   est_wait_time += map[ticket.processType]!! // TODO: BURAYA BAK !!!!!
                }
                binding.remainingTime.setText("Estimated waiting time is: $est_wait_time")

            }
        }
        binding.WorDButton.setOnClickListener {

        }

        binding.paymentButton.setOnClickListener {

        }

        binding.currencyButton.setOnClickListener {


        }

        binding.newAccountButton.setOnClickListener {


        }


        binding.EnterButton.setOnClickListener{
            // TODO: oluşan ticket'ı queue'ya sok.
            // TODO: Sonra Active Queue'ya intent at.

        }



    }




}