package com.example.queuemanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.queuemanagement.databinding.CustomerMenuBinding
import com.google.firebase.auth.FirebaseAuth


class CustomerMenuActivity : AppCompatActivity() {

    private lateinit var binding: CustomerMenuBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CustomerMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var user_uid = intent.getStringExtra("uid")

        binding.button1.setOnClickListener(){
            intent = Intent(this, BranchList::class.java)
            intent.putExtra("uid", user_uid)
            startActivity(intent)
        }
        binding.button3.setOnClickListener {
            Toast.makeText(this@CustomerMenuActivity, "MAP", Toast.LENGTH_SHORT).show()
        }
        binding.button2.setOnClickListener {
            intent = Intent(this, SettingPage::class.java)
            intent.putExtra("uid",user_uid)
            startActivity(intent)

        }



    }




}