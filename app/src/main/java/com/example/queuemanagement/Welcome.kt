package com.example.queuemanagement

import ClassFiles.Customer
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.queuemanagement.databinding.ActivityWelcomeBinding

class Welcome : AppCompatActivity(){

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_welcome)


        var TestButton : Button = findViewById(R.id.QueueButton)

        ////////////  TEST FOR DATABASE CONFIG - TEMP   ////////////
        var textView: TextView = findViewById(R.id.textView1)
        val database = FirestoreDB()
        var myList = mutableListOf<Any>()

        database.listenToChanges("Customers") { querySnapshot ->

            database.getData("Customers") { data ->

                myList = data
                textView.setText(myList.toString())
            }
        }


        TestButton.setOnClickListener(){

            //intent = Intent(this, TestQueue::class.java)
            intent = Intent(this, CustomerMenuActivity::class.java)
            startActivity(intent)


        }






    }
}