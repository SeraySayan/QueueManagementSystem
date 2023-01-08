package com.example.queuemanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.queuemanagement.databinding.CustomerMenuBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.firebase.auth.FirebaseAuth


class CustomerMenuActivity : AppCompatActivity() {
    private lateinit var maps: GoogleMap

    private lateinit var binding: CustomerMenuBinding

    // Object for checking network connectivity
    private lateinit var cld: ConnectionLive


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CustomerMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Check network connectivity and show/hide views accordingly
        checkNetworkConnection()

        val mapFragment = supportFragmentManager.findFragmentById(R.id.myMap) as SupportMapFragment
        mapFragment.getMapAsync {
            maps = it
        }

        var user_uid = intent.getStringExtra("uid")



        binding.button1.setOnClickListener() {
            intent = Intent(this, BranchList::class.java)
            intent.putExtra("uid", user_uid)
            startActivity(intent)
        }

        binding.button2.setOnClickListener {
            intent = Intent(this, SettingPage::class.java)
            intent.putExtra("uid", user_uid)
            startActivity(intent)

        }


        // TEMP BUTTONS FOR DEBUG !!!
        binding.button5.setOnClickListener() {
            intent = Intent(this, CustomerActiveQueue::class.java)
            startActivity(intent)
        }
        binding.button6.setOnClickListener() {
            intent = Intent(this, TestQueue::class.java)
            startActivity(intent)
        }


    }

    // Function for checking network connectivity and showing/hiding views accordingly

    private fun checkNetworkConnection() {
        cld = ConnectionLive(application)
        cld.observe(this) { isConnected ->
            if (isConnected) {
                binding.menuvisible.visibility = View.VISIBLE
                binding.noInternet.visibility = View.GONE
            } else {
                binding.menuvisible.visibility = View.GONE
                binding.noInternet.visibility = View.VISIBLE
            }


        }


    }
}