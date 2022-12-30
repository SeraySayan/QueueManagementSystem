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

        val button1 = findViewById(R.id.button1) as Button
        button1.setOnClickListener {
            Toast.makeText(this@CustomerMenuActivity, "You clicked me.", Toast.LENGTH_SHORT).show()
        }

        binding.button8.setOnClickListener(){

            intent = Intent(this, BranchList::class.java)
            startActivity(intent)
        }
        binding.button10.setOnClickListener(){
            intent = Intent(this,SettingPage::class.java)
            startActivity(intent)

        }




    }




}