package com.example.queuemanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.queuemanagement.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
// This activity is responsible for displaying the login screen to the user
// and handling the user's login attempt.

class MainActivity : AppCompatActivity() {
    // View binding object for accessing views in the layout
    private lateinit var binding: ActivityMainBinding

    // Object for checking network connectivity
    private lateinit var cld : ConnectionLive

    // FirebaseAuth instance for authenticating the user
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Check network connectivity and show/hide views accordingly
        checkNetworkConnection()

        // Initialize FirebaseAuth instance
        firebaseAuth = FirebaseAuth.getInstance()

        // Set click listener for login button
        binding.loginButton.setOnClickListener {
            val email = binding.mail.text.toString()
            val password = binding.password.text.toString()
            // If both email and password fields are not empty
            if (password != "" && email != "") {
                // Attempt to sign in with Firebase

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        // If login is successful, start the TestQueue activity

                        intent = Intent(this, CustomerMenuActivity::class.java)
                        startActivity(intent)


                    } else {
                        // If login fails, check if it's due to an invalid email or password

                        if (firebaseAuth.currentUser?.email.equals(email)) {
                            Toast.makeText(this, "Invalid Password ", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            Toast.makeText(this, "Invalid Email ", Toast.LENGTH_SHORT).show()
                        }


                    }
                }


            }

            // If either email or password field is empty, show a toast message
            else {

                if (password.isEmpty() && email.isEmpty()) {
                    Toast.makeText(this, "Fields should be filled!", Toast.LENGTH_SHORT)
                        .show()
                }
                else if (password.isNotEmpty()) {
                    Toast.makeText(this, "E-mail field should be filled!", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(this, "Password field should be filled!", Toast.LENGTH_SHORT)
                        .show()
                }
            }


        }

        // Set click listener for create account button
        binding.createAccountButton.setOnClickListener {
            // Start the Register activity when the button is clicked
            intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        // Set click listener for forget password button
        binding .forgetPassword.setOnClickListener {
            // Start the ForgetPassword activity when the button is clicked
            intent = Intent(applicationContext, ForgetPassword::class.java)
            startActivity(intent)

        }
    }
    // Function for checking network connectivity and showing/hiding views accordingly

    private fun checkNetworkConnection() {
        cld =  ConnectionLive(application)
        cld.observe(this) { isConnected ->
            if (isConnected) {
                binding.loginVisible.visibility = View.VISIBLE
                binding.noInternet.visibility = View.GONE
            } else {
                binding.loginVisible.visibility = View.GONE
                binding.noInternet.visibility = View.VISIBLE
            }


        }

    }
}


