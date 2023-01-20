package com.example.queuemanagement

import ClassFiles.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import android.widget.AdapterView
import androidx.core.view.get

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.queuemanagement.databinding.ActivityBranchListBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class BranchList : AppCompatActivity() {

    private lateinit var binding : ActivityBranchListBinding
    private lateinit var branchList:ArrayList<Branchs>
    // Object for checking network connectivity
    private lateinit var cld: ConnectionLive

    private var db= FirebaseFirestore.getInstance() // TODO: implement custom class



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBranchListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Check network connectivity and show/hide views accordingly
        checkNetworkConnection()

        db= FirebaseFirestore.getInstance()
        var user_uid = intent.getStringExtra("uid").toString()


        binding.recyclerView.layoutManager =LinearLayoutManager(this)
        branchList = arrayListOf()

        db.collection("Branches").get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    for (data in it.documents){
                        val branch:Branchs? =data.toObject<Branchs>(Branchs::class.java)
                        branchList.add(branch!!)
                    }
                    val adapter = BranchAdapter(branchList, user_uid, this)
                    binding.recyclerView.adapter = adapter

                }
            }
            .addOnFailureListener{
                Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
            }


        binding.mapButton.setOnClickListener() {
            intent = Intent(this, CustomerMenuActivity::class.java)
            intent.putExtra("uid", user_uid)
            startActivity(intent)
        }

        binding.button2.setOnClickListener {
            intent = Intent(this, SettingPage::class.java)
            intent.putExtra("uid", user_uid)
            startActivity(intent)

        }


        // TEMP BUTTONS FOR DEBUG !!!
        binding.button5.setOnClickListener() {
            intent = Intent(this, CustomerActiveQueue::class.java)
            startActivity(intent)
        }
        binding.button6.setOnClickListener() {
            intent = Intent(this, TestQueue::class.java)
            startActivity(intent)
        }






        /* binding.recyclerView.setOnClickListener{


             val intent = Intent(this, CustomerTransaction::class.java)
             startActivity(intent)
             intent.putExtra("id",branchList[position].id)
         }*/        /*adapter.setOnItemClickListener(object : BranchAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            // Handle the click event
                            Log.d("ItemClick", "Item at position $position clicked")
                        }
                    })*/

    }
    // Function for checking network connectivity and showing/hiding views accordingly

    private fun checkNetworkConnection() {
        cld = ConnectionLive(application)
        cld.observe(this) { isConnected ->
            if (isConnected) {
                binding.branchListVisible.visibility = View.VISIBLE
                binding.noInternet.visibility = View.GONE
            } else {
                binding.branchListVisible.visibility = View.GONE
                binding.noInternet.visibility = View.VISIBLE
            }


        }


    }

}



