package com.example.queuemanagement

import android.annotation.SuppressLint
import android.content.Intent
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
            /*var userDoc = database.getDocumentByField("Employee","uid",emp_uid){ data ->
                data?.reference?.update(
                hashMapOf(
                    "branch" to newBranch
                ) as Map<String, Any>
                )
            }*/
            /*intent.putExtra("uid",emp_uid)
            intent = Intent(this, ChangeBranch::class.java)
            startActivity(intent)*/
        }

        binding.button2.setOnClickListener{//deletes employee from Employees collection but not from fireauth
            //delete employee
            var userDoc = database.getDocumentByField("Employees","uid",emp_uid){ data ->
                data?.reference?.delete()
            }
            Toast.makeText(
                this@EmployeeDetails, "Deleted",
                Toast.LENGTH_SHORT
            ).show()
            intent = Intent(this, AdminMenu::class.java)
            startActivity(intent)
        }




    }
}