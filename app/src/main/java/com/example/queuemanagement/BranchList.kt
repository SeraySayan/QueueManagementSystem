package com.example.queuemanagement

import ClassFiles.*
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.queuemanagement.databinding.ActivityBranchListBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore

class BranchList : AppCompatActivity() {

    private lateinit var binding: ActivityBranchListBinding
    private lateinit var branchList: ArrayList<Branch>
    private lateinit var waitingList: ArrayList<Any>
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var userUid: String

    private var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBranchListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        userUid = intent.getStringExtra("uid").toString()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        branchList = arrayListOf()
        waitingList = arrayListOf()

        fetchBranchesAndSortByDistance()
    }

    private fun fetchBranchesAndSortByDistance() {
        db.collection("Branches").get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    for (document in querySnapshot.documents) {
                        val branch: Branch? = document.toObject(Branch::class.java)
                        if (branch != null) {
                            branchList.add(branch)
                        }
                    }
                    sortBranchesByDistance()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, exception.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    private fun sortBranchesByDistance() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    location?.let { currentLocation ->
                        branchList.forEach { branch ->
                            branch.location?.let {
                                val distance = calculateDistance(
                                    currentLocation.latitude,
                                    currentLocation.longitude,
                                    it.latitude,
                                    it.longitude
                                )
                                branch.distance = distance
                            }
                        }
                        branchList.sortBy { it.distance }
                        displaySortedBranches()
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, exception.toString(), Toast.LENGTH_SHORT).show()
                }
        } else {
            // Request location permission if not granted
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }


    private fun calculateDistance(
        currentLatitude: Double,
        currentLongitude: Double,
        branchLatitude: Double,
        branchLongitude: Double
    ): Float {
        val results = FloatArray(1)
        android.location.Location.distanceBetween(
            currentLatitude,
            currentLongitude,
            branchLatitude,
            branchLongitude,
            results
        )
        return results[0]
    }

    private fun displaySortedBranches() {
        val adapter = BranchAdapter(branchList, userUid, this)
        binding.recyclerView.adapter = adapter
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}
