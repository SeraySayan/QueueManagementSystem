package com.example.myapplication
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class BranchListMainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var branchList:ArrayList<Branchs>
    private var db= Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db= FirebaseFirestore.getInstance()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager =LinearLayoutManager(this)
        branchList = arrayListOf()

        db.collection("1").get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    for (data in it.documents){
                        val branch:Branchs? =data.toObject<Branchs>(Branchs::class.java)
                        branchList.add(branch!!)
                    }
                    recyclerView.adapter = BranchAdapter(branchList)
                }
            }
            .addOnFailureListener{
                Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
            }
    }
}