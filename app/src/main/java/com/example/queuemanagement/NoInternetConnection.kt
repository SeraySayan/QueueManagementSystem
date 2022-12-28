package com.example.queuemanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.queuemanagement.databinding.ActivityNoInternetConnectionBinding

// This activity is responsible for displaying a message to the user
// when there is no internet connection.

class NoInternetConnection : AppCompatActivity() {
    // View binding object for accessing views in the layout

    private lateinit var binding: ActivityNoInternetConnectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoInternetConnectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}