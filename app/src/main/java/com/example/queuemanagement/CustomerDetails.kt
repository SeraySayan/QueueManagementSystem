package com.example.queuemanagement

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.queuemanagement.databinding.ActivityCustomerDetailsBinding

class CustomerDetails : AppCompatActivity() {

        private lateinit var binding: ActivityCustomerDetailsBinding
        var database = FirestoreDB()

        @SuppressLint("SetTextI18n")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityCustomerDetailsBinding.inflate(layoutInflater)
            setContentView(binding.root)

        val cus_uid = intent.getStringExtra("uid")
        val cName =intent.getStringExtra("name")
        val cSurname = intent.getStringExtra("surname")
        val cEmail = intent.getStringExtra("email")
        val cDoB =intent.getStringExtra("reg_date")
        val cPriority =intent.getIntExtra("priority",1).toString()
        binding.textView1.setText(cName)
        binding.textView2.setText(cSurname)
        binding.textView3.setText("Uid:  $cus_uid")
        binding.textView4.setText("E-mail:  $cEmail")
        binding.textView5.setText("Birthday:  $cDoB")
        binding.textView6.setText("Priority:  $cPriority")
        binding.button1.setOnClickListener{
            //delete customer
        }

        binding.vip.setOnCheckedChangeListener { _, isChecked ->
            val message = if (isChecked){
                "$cName is V.I.P. customer now."
                //cPriority+=1
            }
            else{
                "$cName is not V.I.P. customer now."
               // cPriority= cPriority-1
            }
            Toast.makeText(
                this@CustomerDetails, message,
                Toast.LENGTH_SHORT
            ).show()
        }




    }
}