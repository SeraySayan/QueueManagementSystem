package com.example.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.myapplication.R.id.testView1
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Test: Barışın commiti
        // TEST FOR DATABASE CONFIG - TEMP /////////////////////////
        var textView: TextView = findViewById(testView1)
        var mylist = StringBuilder()
        db.collection("PersonList")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    println("${document.id} => ${document.data}")
                    mylist.append("${document.data}")
                }
                textView.setText(mylist)
            }
            .addOnFailureListener { exception ->
                println("Error getting documents.")
            }
        ////////////////////////////////////////////////////////////


    }
}