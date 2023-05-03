package com.example.queuemanagement

import ClassFiles.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.queuemanagement.databinding.ActivityBranchListBinding
import com.google.firebase.firestore.FirebaseFirestore

class BranchList : AppCompatActivity() {

    private lateinit var binding : ActivityBranchListBinding
    private lateinit var branchList:ArrayList<Branch>
    private lateinit var waitingList:ArrayList<Any>

    private var db= FirebaseFirestore.getInstance() // TODO: implement custom class



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBranchListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db= FirebaseFirestore.getInstance()
        var user_uid = intent.getStringExtra("uid").toString()



        binding.recyclerView.layoutManager =LinearLayoutManager(this)
        branchList = arrayListOf()
        waitingList = arrayListOf()

        db.collection("Branches").get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    for (data in it.documents){
                        val branch:Branch? =data.toObject(Branch::class.java)
                        branchList.add(branch!!)
                    }
                    val adapter = BranchAdapter(branchList, user_uid, this)
                    binding.recyclerView.adapter = adapter

                }
            }
            .addOnFailureListener{
                Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
            }







    }

}



