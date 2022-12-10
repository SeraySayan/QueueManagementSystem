package com.example.queuemanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var db = Firebase.firestore

        // TEST FOR DATABASE CONFIG - TEMP /////////////////////////
        var textView: TextView = findViewById(R.id.textView1)
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