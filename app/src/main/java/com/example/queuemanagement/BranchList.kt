package com.example.queuemanagement

import ClassFiles.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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


        binding.recyclerView.layoutManager =LinearLayoutManager(this)
        branchList = arrayListOf()

        db.collection("1").get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    for (data in it.documents){
                        val branch:Branchs? =data.toObject<Branchs>(Branchs::class.java)
                        branchList.add(branch!!)
                    }
                    binding.recyclerView.adapter = BranchAdapter(branchList)
                }
            }
            .addOnFailureListener{
                Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
            }


       /* binding.recyclerView.setOnClickListener{


            val intent = Intent(this, CustomerTransaction::class.java)
            startActivity(intent)
            intent.putExtra("id",branchList[position].id)
        }*/

    }

}


