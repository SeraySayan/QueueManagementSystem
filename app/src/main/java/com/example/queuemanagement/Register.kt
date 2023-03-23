package com.example.queuemanagement


import ClassFiles.Customer
import ClassFiles.Employee
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.queuemanagement.databinding.ActivityRegisterBinding
import com.google.firebase.auth.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
// This activity is responsible for handling the user's registration process.

class Register : AppCompatActivity() {
    // View binding object for accessing views in the layout

    private lateinit var binding: ActivityRegisterBinding
    // FirebaseAuth instance for creating a new user

    private lateinit var firebaseAuth: FirebaseAuth

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
        val database = FirestoreDB()


        // Get the values entered by the user in the edit text fields
        val name = binding.name.text.toString()
        val surname = binding.surname.text.toString()
        val email = binding.mail.text.toString()
        val password = binding.password.text.toString()
        val repassword = binding.rePasswaord.text.toString()
        val birthday = binding.birthday.text.toString()

        val localDate = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        val current = LocalDateTime.now()
        val age =  current.year - localDate.year
        var priority=1
        if(age>=65){
            priority= 2
        }

        /*val dateFormat = SimpleDateFormat("yyyy")

        val date = dateFormat.format(birthday)
        val birthYear = date.toInt()

        val cDate  = Date()
        val currentYear = dateFormat.format(cDate)
        val current = currentYear.toInt()

        var age = birthYear - current

            Toast.makeText(this,age,Toast.LENGTH_SHORT).show()*/




        // If all fields are not empty
        if (email.isNotEmpty()&& password.isNotEmpty()&&repassword.isNotEmpty()&&birthday.isNotEmpty()){

            // If the password and confirm password fields match
            if (password == repassword){

                // Attempt to create a new user with the provided email and password
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{ it ->

                    if(it.isSuccessful){
                        // If the user is successfully created, store their information in the database
                        if (email.split("@")[1] == "employee.com"){

                            val employee = firebaseAuth.currentUser?.let { it1 -> Employee(it1.uid,name,surname,email,password,birthday,age) }
                            if (employee != null) {
                                database.addData("Employees",employee)
                            }

                        }
                        else if(email.split("@")[1] != "employee.com"){

                            val users = firebaseAuth.currentUser?.let { it1 -> Customer(it1.uid,name,surname,email,password,birthday,age,priority) }
                            if (users != null) {
                                database.addData("Customers",users)
                            }

                        }

                        val intent = Intent (this,MainActivity::class.java)
                        startActivity(intent)
                    }

                }
                    .addOnFailureListener {
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