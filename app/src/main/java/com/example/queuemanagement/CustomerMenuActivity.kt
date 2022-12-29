package com.example.queuemanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.queuemanagement.databinding.CustomerMenuBinding


class CustomerMenuActivity : AppCompatActivity() {

    private lateinit var binding: CustomerMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CustomerMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.button1.setOnClickListener(){
            intent = Intent(this, BranchList::class.java)
            startActivity(intent)
        }
        binding.button3.setOnClickListener {
            Toast.makeText(this@CustomerMenuActivity, "MAP", Toast.LENGTH_SHORT).show()
        }
        binding.button2.setOnClickListener {
            Toast.makeText(this@CustomerMenuActivity, "SETTINGS", Toast.LENGTH_SHORT).show()

        }




    }




}