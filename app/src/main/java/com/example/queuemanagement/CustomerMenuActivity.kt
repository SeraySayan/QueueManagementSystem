package com.example.queuemanagement

import ClassFiles.Branchs
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.queuemanagement.databinding.CustomerMenuBinding
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.type.LatLng


class CustomerMenuActivity : AppCompatActivity() {
    private lateinit var maps: GoogleMap
    private lateinit var database: FirebaseFirestore
    private var db= FirebaseFirestore.getInstance()
    private lateinit var branchList:ArrayList<Branchs>


    private lateinit var arrayList: ArrayList<com.google.android.gms.maps.model.LatLng>

    private lateinit var binding: CustomerMenuBinding

    // Object for checking network connectivity
    private lateinit var cld: ConnectionLive


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CustomerMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Check network connectivity and show/hide views accordingly
        checkNetworkConnection()

        val mapFragment = supportFragmentManager.findFragmentById(R.id.myMap) as SupportMapFragment
        mapFragment.getMapAsync {
            maps = it
            onMapReady(maps)
        }


        db= FirebaseFirestore.getInstance()


        var user_uid = intent.getStringExtra("uid").toString()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
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



        binding.button1.setOnClickListener() {
            intent = Intent(this, BranchList::class.java)
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


    }

    // Function for checking network connectivity and showing/hiding views accordingly

    private fun checkNetworkConnection() {
        cld = ConnectionLive(application)
        cld.observe(this) { isConnected ->
            if (isConnected) {
                binding.menuvisible.visibility = View.VISIBLE
                binding.noInternet.visibility = View.GONE
            } else {
                binding.menuvisible.visibility = View.GONE
                binding.noInternet.visibility = View.VISIBLE
            }


        }


    }
   /* private fun setList(){

        db.collection("Branches").get().addOnSuccessListener{
            if(!it.isEmpty){
                for (data in it.documents){
                    val place: com.google.android.gms.maps.model.LatLng = com.google.android.gms.maps.model.LatLng(
                        data.get("latitude") as Double,
                        data.get("longitude") as Double
                    )

                    arrayList.add(place)
                }
            }
        }



    }*/
    private fun onMapReady(googleMap:GoogleMap ){
        maps = googleMap

       val TamWorth = com.google.android.gms.maps.model.LatLng(-31.083332, 150.916672)
       val YarımWorth = com.google.android.gms.maps.model.LatLng(-30.083332, 155.916672)
       maps.addMarker(MarkerOptions().position(TamWorth).title("TamWorth"))
       maps.animateCamera(CameraUpdateFactory.zoomTo(15.0f))
       maps.moveCamera(CameraUpdateFactory.newLatLng(TamWorth))

       maps.addMarker(MarkerOptions().position(YarımWorth).title("YarımWorth"))
       maps.animateCamera(CameraUpdateFactory.zoomTo(15.0f))
       maps.moveCamera(CameraUpdateFactory.newLatLng(YarımWorth))


        /*for(element in arrayList)
        {
            maps.addMarker(MarkerOptions().position(element).title("branch"))
            maps.animateCamera(CameraUpdateFactory.zoomTo(15.0f))
            maps.moveCamera(CameraUpdateFactory.newLatLng(element))

        }*/
    }


}


