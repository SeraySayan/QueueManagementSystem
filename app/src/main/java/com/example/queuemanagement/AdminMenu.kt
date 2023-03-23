package com.example.queuemanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.queuemanagement.databinding.ActivityAdminMenuBinding


class AdminMenu : AppCompatActivity() {
    lateinit var binding: ActivityAdminMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.button.setOnClickListener {
            intent = Intent(this, CustomerList::class.java)
            startActivity(intent)

        }

        binding.button2.setOnClickListener {
            intent = Intent(this, EmployeeList::class.java)
            startActivity(intent)

        }
    }
}