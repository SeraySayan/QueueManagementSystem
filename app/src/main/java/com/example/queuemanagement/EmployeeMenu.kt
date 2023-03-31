package com.example.queuemanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.queuemanagement.databinding.ActivityEmployeeMenuBinding
import com.example.queuemanagement.databinding.ActivityMainBinding

class EmployeeMenu : AppCompatActivity() {

    lateinit var binding: ActivityEmployeeMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // getting uid of the employee
        val user_uid = intent.getStringExtra("uid")

        binding.button.setOnClickListener {
            intent = Intent(this, EmployeeQueue::class.java)
            intent.putExtra("uid", user_uid)
            startActivity(intent)

        }

        binding.button2.setOnClickListener {
            intent = Intent(this, SettingPage::class.java)
            startActivity(intent)

        }


    }
}