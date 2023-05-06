package com.example.queuemanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.queuemanagement.databinding.ActivityCustomerQueueFinishedBinding


class CustomerQueueFinished : AppCompatActivity() {


    lateinit var binding: ActivityCustomerQueueFinishedBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerQueueFinishedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var uid = intent.getStringExtra("uid").toString()




        binding.button4.setOnClickListener(){

            intent = Intent(this, CustomerMenu::class.java)
            intent.putExtra("uid",uid)
            startActivity(intent)

        }





    }




}