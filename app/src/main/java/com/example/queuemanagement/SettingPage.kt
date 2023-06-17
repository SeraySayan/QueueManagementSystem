package com.example.queuemanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.PopupWindow
import com.example.queuemanagement.databinding.ActivityMainBinding
import com.example.queuemanagement.databinding.ActivityPopUpBinding
import com.example.queuemanagement.databinding.ActivitySettingPageBinding
// This activity is responsible for displaying the setting page for the app.

class SettingPage : AppCompatActivity() {
    // View binding object for accessing views in the layout

    private lateinit var binding: ActivitySettingPageBinding
    // Object for checking network connectivity
    private lateinit var cld : ConnectionLive

    private var database = FirestoreDB()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Check network connectivity and show/hide views accordingly

        checkNetworkConnection()

        var user_uid = intent.getStringExtra("uid")
        database.getDocumentByField("Customers","uid",user_uid){
            binding.userinfo.text = it?.get("email")?.toString()
        }




        binding.logOutClick.setOnClickListener{


            val intent = Intent(this, PopUp::class.java)
            startActivity(intent)
        }
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