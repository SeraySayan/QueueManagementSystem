package com.example.queuemanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.queuemanagement.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.loginButton.setOnClickListener {
            val email = binding.mail.text.toString()
            val password = binding.password.text.toString()
            //if users enter the information
            if (password != "" && email != "") {

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        intent = Intent(this, Welcome::class.java)
                        startActivity(intent)


                    } else {
                        if (firebaseAuth.currentUser?.email.equals(email)) {
                            Toast.makeText(this, "Invalid Password ", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            Toast.makeText(this, "Invalid Email ", Toast.LENGTH_SHORT).show()
                        }


                    }
                }


            }

            //if there are any empty fields
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

        binding.createAccountButton.setOnClickListener {
            intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        binding .forgetPassword.setOnClickListener {
            intent = Intent(applicationContext, ForgetPassword::class.java)
            startActivity(intent)

        }
    }


}
