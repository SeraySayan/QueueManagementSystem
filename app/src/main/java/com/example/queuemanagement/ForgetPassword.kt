package com.example.queuemanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.example.queuemanagement.databinding.ActivityForgetPasswordBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

//This is an Activity class that allows the user to reset their password by entering their email address.
// It uses Firebase authentication to send a password reset email to the user's email address.
//
//The ForgetPassword class has a layout file, activity_forget_password, which is inflated in the onCreate method
// using the ActivityForgetPasswordBinding class. The layout contains a TextInputEditText field for the user to enter
// their email address and a button to initiate the password reset process.
//
//The ForgetPassword class also has a field, cld, which is an instance of the ConnectionLive class.
// The checkNetworkConnection method is used to observe the connectivity status of the device and show or hide the layout
// elements depending on the availability of a network connection.
//
//The checkEmail method is used to validate the email address entered by the user.
// It checks if the email field is empty or if the email address is in a valid format.
// If the email address is invalid, a toast message is displayed to inform the user.
//
//When the user clicks the reset button, the email address is checked for validity and, if it is valid, a password
// reset email is sent to the user's email address using Firebase authentication. If the password reset email is sent successfully,
// a toast message is displayed to inform the user and the activity is finished.

class ForgetPassword : AppCompatActivity() {
    private lateinit var binding: ActivityForgetPasswordBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase


    private lateinit var cld : ConnectionLive

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkNetworkConnection()

        val auth =FirebaseAuth.getInstance()
        binding.resetButton.setOnClickListener {
            val email = binding.resetmail.text.toString()
            Toast.makeText(this,email,Toast.LENGTH_SHORT).show()
            if (checkEmail()){
                auth.sendPasswordResetEmail(binding.resetmail.text.toString()).addOnCompleteListener(){
                    if (it.isSuccessful){
                        Toast.makeText(this,"The email is sent for reset password!",Toast.LENGTH_SHORT).show()
                        val intent = Intent (this,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                }
            }

        }


    }

    private fun checkNetworkConnection() {
        cld =  ConnectionLive(application)
        cld.observe(this) { isConnected ->
            if (isConnected) {
                binding.ForgetVisible.visibility = View.VISIBLE
                binding.noInternet.visibility = View.GONE
            } else {
                binding.ForgetVisible.visibility = View.GONE
                binding.noInternet.visibility = View.VISIBLE
            }


        }
    }

    private fun checkEmail():Boolean{
        val email = binding.resetmail.text.toString()
        return if (email == ""){
            Toast.makeText(this,"This field should be filled!",Toast.LENGTH_SHORT).show()
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this,"Check Email format!",Toast.LENGTH_SHORT).show()
            false

        } else {
            true
        }
    }
}