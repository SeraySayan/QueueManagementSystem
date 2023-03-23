package com.example.queuemanagement

import ClassFiles.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

    private var db= FirebaseFirestore.getInstance() // TODO: implement custom class



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBranchListBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

}



