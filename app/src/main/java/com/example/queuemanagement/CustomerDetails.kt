package com.example.queuemanagement
import com.google.firebase.firestore.FirebaseFirestore.getInstance

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.queuemanagement.databinding.ActivityCustomerDetailsBinding


class CustomerDetails : AppCompatActivity() {

        private lateinit var binding: ActivityCustomerDetailsBinding
        var database = FirestoreDB()


    @SuppressLint("SetTextI18n")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityCustomerDetailsBinding.inflate(layoutInflater)
            setContentView(binding.root)

        val cus_uid = intent.getStringExtra("uid")
        val cName =intent.getStringExtra("name")
        val cSurname = intent.getStringExtra("surname")
        val cEmail = intent.getStringExtra("email")
        val cDoB =intent.getStringExtra("reg_date")
        var cPriority =intent.getIntExtra("priority",1)
            binding.textView1.text = cName
            binding.textView2.text = cSurname
            binding.textView3.text = "Uid:  $cus_uid"
            binding.textView4.text = "E-mail:  $cEmail"
            binding.textView5.text = "Birthday:  $cDoB"
            binding.textView6.text = "Priority:  $cPriority"
        binding.button1.setOnClickListener{
            //deletes customer from Customers collection but not from fireauth
            var userDoc = database.getDocumentByField("Customers","uid",cus_uid){ data ->
                data?.reference?.delete()
            }
            Toast.makeText(
                this@CustomerDetails, "Deleted",
                Toast.LENGTH_SHORT
            ).show()
            intent = Intent(this, AdminMenu::class.java)
            startActivity(intent)
        }
        binding.vip.setOnCheckedChangeListener { _, isChecked ->
            var message=""
             if (isChecked){
                message= "$cName is V.I.P. customer now."
                cPriority += 1
            }
            else{
                message="$cName is not V.I.P. customer now."
                cPriority -= 1
            }
            binding.textView6.text = "Priority:  $cPriority"

            var userDoc = database.getDocumentByField("Customers","uid",cus_uid){ data ->
                data?.reference?.update(
                    hashMapOf(
                        "priority" to cPriority
                    ) as Map<String, Any>
                )
            }
            Toast.makeText(
                this@CustomerDetails, message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}