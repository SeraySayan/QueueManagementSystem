package com.example.queuemanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.queuemanagement.databinding.ActivityMainBinding
import com.example.queuemanagement.databinding.ActivitySettingPageBinding
import com.google.firebase.auth.FirebaseAuth

// This activity is responsible for displaying the setting page for the app.

class SettingPage : AppCompatActivity() {
    // View binding object for accessing views in the layout

    private lateinit var binding: ActivitySettingPageBinding
    private lateinit var firebaseAuth: FirebaseAuth

    // Object for checking network connectivity
    private lateinit var cld : ConnectionLive

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        val email = firebaseAuth.currentUser?.email
        binding.userinfo.text = email
        // Check network connectivity and show/hide views accordingly

        checkNetworkConnection()
    }
    // Function for checking network connectivity and showing/hiding views accordingly

    private fun checkNetworkConnection() {
        cld =  ConnectionLive(application)
        cld.observe(this) { isConnected ->
            if (isConnected) {
                binding.SettingVisible.visibility = View.VISIBLE
                binding.noInternet.visibility = View.GONE
            } else {
                binding.SettingVisible.visibility = View.GONE
                binding.noInternet.visibility = View.VISIBLE
            }


        }
    }
}