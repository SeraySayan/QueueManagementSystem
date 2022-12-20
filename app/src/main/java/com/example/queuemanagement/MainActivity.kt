package com.example.queuemanagement

import ClassFiles.* // Importing .kt Class Files from different folder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


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

        var testObject = Ticket()
        database.addData("TestCollection/testdoc1/testsubcol", testObject)

        /////////////////////// OOP TESTS //////////////////////////

        var c1 = Customer()
        var e1 = Employee()
        var t1 = Ticket()
        var t2 = c1.createTicket("TaxOperation")

        ////////////////////////////////////////////////////////////
    }



}