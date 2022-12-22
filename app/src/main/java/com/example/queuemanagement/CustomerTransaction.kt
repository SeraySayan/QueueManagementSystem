package com.example.queuemanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ClassFiles.*
import android.content.Intent
import android.widget.Button
import com.example.queuemanagement.databinding.ActivityCustomerTransactionBinding
import com.google.firebase.auth.FirebaseAuth

class CustomerTransaction : AppCompatActivity() {
    public var processTime =0
    public var processType=" "

    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var binding: ActivityCustomerTransactionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val myButton= findViewById<Button>(R.id.WorDButton)
        myButton.setOnClickListener {

            processTime= 20
            processType="Withdraw/Deposit Money"
            val intent = Intent(this, CustomerActiveQueue::class.java)
            startActivity(intent)
            intent.putExtra("processTime",processTime)
            intent.putExtra("processType",processType)
        }

        binding.paymentButton.setOnClickListener {

            processTime= 25
            processType="Payments"
            val intent = Intent(this, CustomerActiveQueue::class.java)
            startActivity(intent)
            intent.putExtra("processTime",processTime)
            intent.putExtra("processType",processType)
        }

        binding.currencyButton.setOnClickListener {

            processTime= 30
            processType="Investment to Currency"
            val intent = Intent(this, CustomerActiveQueue::class.java)
            startActivity(intent)
            intent.putExtra("processTime",processTime)
            intent.putExtra("processType",processType)
        }

        binding.newAccountButton.setOnClickListener {
            /*if(Customer.activeTicket.processType.equals("Open New Account"))
            {
                val intent = Intent(this, NewAccQueue::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "You are already in the queue for this transaction ", Toast.LENGTH_SHORT).show()
                 val intent = Intent(this, EnterQueue::class.java)
                startActivity(intent)
            }*/
            processTime= 40
            processType="Open New Account"
            val intent = Intent(this, CustomerActiveQueue::class.java)
            startActivity(intent)
            intent.putExtra("processTime",processTime)
            intent.putExtra("processType",processType)
        }




    }




}