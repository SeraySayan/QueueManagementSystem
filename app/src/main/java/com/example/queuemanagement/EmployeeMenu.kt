package com.example.queuemanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.queuemanagement.databinding.ActivityEmployeeMenuBinding
import com.example.queuemanagement.databinding.ActivityMainBinding


// TODO : binding ekle


class EmployeeMenu : AppCompatActivity() {

    lateinit var binding: ActivityEmployeeMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button2.setOnClickListener {
            intent = Intent(this, SettingPage::class.java)
            startActivity(intent)

        }

    }
}