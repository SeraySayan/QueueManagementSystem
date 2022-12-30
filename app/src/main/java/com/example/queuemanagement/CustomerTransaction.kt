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
import com.google.firebase.ktx.Firebase


class CustomerTransaction : AppCompatActivity() {
    public var processTime =0
    public var processType=" "

    lateinit var binding: ActivityCustomerTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val database = FirestoreDB()

        val c2 = Customer(1223,"Aslı","Yıldırım",
            "aslı.yıldırım@metu.edu.tr","654321",
            "25/12/2022","+905345678","İstanbul")

//TODO: take queue from branch
        /*var branches = mutableListOf<Any>()

        database.listenToChanges("/Branches/Dcufvp3mIsGAiBtw385l") { querySnapshot ->

            database.getData("/Branches/Dcufvp3mIsGAiBtw385l") { data ->

                branches = data
            }
        }*/

        var queue = mutableListOf<Any>()
        //var queue = ArrayList<Ticket>()

        database.listenToChanges("/Queue/queue1/TicketsInQueue") { querySnapshot ->

            database.getQueue("/Queue/queue1/TicketsInQueue") { tickets ->

                queue = tickets
            }
        }
        val count= queue.count()
        var totalTime = 0
        //dropdown
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
                if(position==0)
                {
                    processTime= 20
                    processType="Withdraw/Deposit Money"
                }
                else if(position==1){
                    processTime= 25
                    processType="Payments"
                }
                else if(position==2){
                    processTime= 30
                    processType="Investment to Currency"
                }
                else if(position==3){
                    processTime= 40
                    processType="Open New Account"
                }
                // totalTime= queue[queue.size-1].total_waited_time + processTime
                binding.peopleCount.setText("There are $count customers in the queue.")
                //binding.remainingTime.setText("Estimated remaining time:$totalTime")
                binding.remainingTime.setText(queue.toString())
            }

            @SuppressLint("SetTextI18n")
            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.remainingTime.setText("Nothing selected")
                binding.peopleCount.setText("Nothing selected")
            }

        }


        binding.EnterButton.setOnClickListener(){

           // database.addData("/Queue/queue1/TicketsInQueue",Ticket( queue[queue.size-1].id+1,0, processType,totalTime,c2.id))
            val intent = Intent(this, CustomerActiveQueue::class.java)
            startActivity(intent)
            //intent.putExtra("customer_id",c2.id)
        }



    }




}