package com.example.queuemanagement

import ClassFiles.*
import android.R
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.queuemanagement.databinding.ActivityCustomerTransactionBinding

//TODO: processType seçim UI daha iyi olabilir. Bir de bu sayfayı branchList sonrasında çağıracaz
class CustomerTransaction : AppCompatActivity() {
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


        // Getting selected queue and user uid from previous activity
        val selected_queue = intent.getStringExtra("queue_location").toString()
        val user_uid = intent.getStringExtra("uid").toString()
        var priority=1//initialization of priority
        // TODO:ilk bunu alıp yanlış değerleri gösteriyor, gir çık yapınca doğru değerleri gösteriyor,düzelt
        //taking the priority from database
        var userDoc = database.getDocumentByField("Customers","uid",user_uid){ data ->
             priority =  data?.get("priority").toString().toInt()
        }

        database.listenToChanges(selected_queue){querySnapshot ->

            database.getTransactionQueue(selected_queue, priority){ queueSize, wait_times->
                val queue_size =  queueSize.size  // getting the queue size
                binding.peopleCount.setText("There are $queue_size customers in the line")

                var est_wait_time = 0
                for (x in wait_times) {
                    est_wait_time += x
                }
                binding.remainingTime.setText("Estimated waiting time is: $est_wait_time min.")

            }

        }

        /*database.listenToChanges(selected_queue) { querySnapshot ->

            //TODO: TEST versiyonunda hata var. normal hali çalışıyor. queue size çalışıyor, timing için class yapmayı dene
            database.getQueueTEST(selected_queue) { tickets ->

                var queue_size =  tickets.size  // getting the queue size
                binding.peopleCount.setText("There are $queue_size customers in the line")

                var est_wait_time = 0
                for (ticket in tickets){
                    est_wait_time += map[ticket.processType]!! // TODO: BURAYA BAK !!!!!
                }
                binding.remainingTime.setText("Estimated waiting time is: $est_wait_time min.")

            }
        }*/

        var processType = ""
        // Dropdown menu
        val elements= arrayOf("Withdraw/Deposit Money","Payments","Investment to Currency","Open New Account")
        val adapter= ArrayAdapter(this,R.layout.simple_spinner_item,elements)
        binding.spinner.setAdapter(adapter)
        binding.spinner.onItemSelectedListener = object:
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


        binding.EnterButton.setOnClickListener{

            var userDoc = database.getDocumentByField("Customers","uid",user_uid){ data ->

                var pri =  data?.get("priority").toString().toInt()

                database.addData(selected_queue,Ticket(0,pri,processType,user_uid))

                intent = Intent(this, CustomerActiveQueue::class.java)
                intent.putExtra("queue",selected_queue)
                intent.putExtra("uid", user_uid)
                intent.putExtra("priority",pri)//sending to the active queue
                startActivity(intent)

            }





            // TODO: oluşan ticket'ı queue'ya sok.
            // TODO: Sonra Active Queue'ya intent at.

        }



    }




}