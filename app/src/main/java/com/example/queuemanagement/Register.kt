package com.example.queuemanagement


import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Toast
import com.example.queuemanagement.databinding.ActivityRegisterBinding
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore

class Register : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseFirestore



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        binding.RegisterButton.setOnClickListener{
            performRegister()


        }
    }

    private fun performRegister(){

        val email = binding.mail.text.toString()
        val password = binding.password.text.toString()
        val repassword = binding.rePassword.text.toString()
        val birthday = binding.birthday.text.toString()

        if (email.isNotEmpty()&& password.isNotEmpty()&&repassword.isNotEmpty()&&birthday.isNotEmpty()){

            if (password == repassword){

                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{ it ->

                    if(it.isSuccessful){
                        val users = Users(email,password,birthday,firebaseAuth.currentUser!!.uid)
                        database.collection("Customers").add(users).addOnCompleteListener{
                            if (it.isSuccessful){
                                val intent = Intent(this,MainActivity::class.java)
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