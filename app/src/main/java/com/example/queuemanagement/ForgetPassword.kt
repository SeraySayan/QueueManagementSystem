package com.example.queuemanagement

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.queuemanagement.databinding.ActivityForgetPasswordBinding


class ForgetPassword : AppCompatActivity() {
    private lateinit var binding: ActivityForgetPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //val auth =FirebaseAuth.getInstance()
        binding.resetButton.setOnClickListener {
            val email = binding.resetmail.text.toString()
            Toast.makeText(this,email,Toast.LENGTH_SHORT).show()
            if (checkEmail()){
                //auth.sendPasswordResetEmail(binding.resetmail.text.toString()).addOnCompleteListener(){
                    //if (it.isSuccessful){
                        Toast.makeText(this,"The email is sent for reset password!",Toast.LENGTH_SHORT).show()
                        val intent = Intent (this,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                   // }

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


