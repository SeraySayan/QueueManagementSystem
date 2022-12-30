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

        binding.viewMap.setOnClickListener {
          //  intent = Intent(this, MapsPage::class.java)
          //  startActivity(intent)

        }
        binding.settings.setOnClickListener(){

           // intent = Intent(this, Settings::class.java)
           // startActivity(intent)


        }
        binding.joinQueue.setOnClickListener(){

            //intent = Intent(this, BranchList::class.java)
            intent = Intent(this, CustomerTransaction::class.java)
            startActivity(intent)
        }




    }




}