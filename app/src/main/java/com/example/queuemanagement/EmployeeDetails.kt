package com.example.queuemanagement

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.queuemanagement.databinding.ActivityEmployeeDetailsBinding

class EmployeeDetails : AppCompatActivity() {
    private lateinit var binding: ActivityEmployeeDetailsBinding
    var database = FirestoreDB()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val emp_uid = intent.getStringExtra("uid")
        val eName =intent.getStringExtra("name")
        val eSurname = intent.getStringExtra("surname")
        val eEmail = intent.getStringExtra("email")
        val eDoB =intent.getStringExtra("reg_date")
        val eBranch =intent.getStringExtra("branch")

        binding.textView1.setText(eName)
        binding.textView2.setText(eSurname)
        binding.textView3.setText("Uid:  $emp_uid")
        binding.textView4.setText("E-mail:  $eEmail")
        binding.textView5.setText("Birthday:  $eDoB")
        binding.textView6.setText("Branch:  $eBranch")

        binding.button1.setOnClickListener{
           //change branch
        }

        binding.button2.setOnClickListener{
            //delete employee
        }




    }
}