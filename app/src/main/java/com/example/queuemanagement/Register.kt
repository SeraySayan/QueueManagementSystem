package com.example.queuemanagement


import android.content.Intent

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.queuemanagement.databinding.ActivityRegisterBinding
import com.google.firebase.auth.*
import com.google.firebase.database.FirebaseDatabase
import java.util.*
// This activity is responsible for handling the user's registration process.

class Register : AppCompatActivity() {
    // View binding object for accessing views in the layout

    private lateinit var binding: ActivityRegisterBinding
    // FirebaseAuth instance for creating a new user

    private lateinit var firebaseAuth: FirebaseAuth

    // FirebaseDatabase instance for storing the user's information
    private lateinit var database: FirebaseDatabase
    // Object for checking network connectivity

    private lateinit var cld : ConnectionLive

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Check network connectivity and show/hide views accordingly

        checkNetworkConnection()

        // Initialize FirebaseAuth and FirebaseDatabase instances

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        // Set click listener for the register button

        binding.RegisterButton.setOnClickListener{
            // Attempt to register the user
            performRegister()


        }
    }
    // Function for checking network connectivity and showing/hiding views accordingly
    private fun checkNetworkConnection() {
        cld =  ConnectionLive(application)
        cld.observe(this) { isConnected ->
            if (isConnected) {
                binding.registerVisible.visibility = View.VISIBLE
                binding.noInternet.visibility = View.GONE
            } else {
                binding.registerVisible.visibility = View.GONE
                binding.noInternet.visibility = View.VISIBLE
            }


        }

    }

    // Function for handling the user registration process
    private fun performRegister(){

        // Get the values entered by the user in the edit text fields
        val email = binding.mail.text.toString()
        val password = binding.password.text.toString()
        val repassword = binding.rePasswaord.text.toString()
        val birthday = binding.birthday.text.toString()

        // If all fields are not empty
        if (email.isNotEmpty()&& password.isNotEmpty()&&repassword.isNotEmpty()&&birthday.isNotEmpty()){

            // If the password and confirm password fields match
            if (password == repassword){

                // Attempt to create a new user with the provided email and password
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{ it ->

                    if(it.isSuccessful){
                        // If the user is successfully created, store their information in the database
                        val databaseRef = database.getReferenceFromUrl("https://bankq-a7a84-default-rtdb.firebaseio.com/").child("users")
                        val users = Users(email,password,birthday,firebaseAuth.currentUser!!.uid)
                        databaseRef.setValue(users).addOnCompleteListener{
                            if (it.isSuccessful){
                                // If the user's information is successfully stored, start the MainActivity

                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                            }
                        }



                    }

                }
                    .addOnFailureListener {
                        /*val formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH)
                        } else {
                            TODO("VERSION.SDK_INT < O")
                        }
                        val date = LocalDate.parse(birthday, formatter)*/
                        when (it) {
                            is FirebaseAuthUserCollisionException -> {

                                Toast.makeText(this,"Email address is already in use!",Toast.LENGTH_SHORT).show()

                            }
                            is FirebaseAuthWeakPasswordException -> {
                                Toast.makeText(this,"Password is not strong!",Toast.LENGTH_SHORT).show()

                            }
                            is FirebaseAuthInvalidCredentialsException -> {

                                Toast.makeText(this,"Email address is not available format!",Toast.LENGTH_SHORT).show()

                            }
                            /*else if (!date.format(formatter).equals(birthday)){
                                Toast.makeText(this,"Birthday format is not available!",Toast.LENGTH_SHORT).show()

                            }*/
                            else -> {
                                Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()

                            }
                        }
                    }




            }
            else {
                Toast.makeText(this,"Password is not matching!",Toast.LENGTH_SHORT).show()

            }
        }

        else {
            Toast.makeText(this,"Empty Fields is not Allowed!",Toast.LENGTH_SHORT).show()

        }

    }


}