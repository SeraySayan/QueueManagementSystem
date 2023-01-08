package com.example.queuemanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.queuemanagement.databinding.ActivityViewMapBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment

class ViewMap : AppCompatActivity() {

    private lateinit var maps: GoogleMap
    private lateinit var binding: ActivityViewMapBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.myMap) as SupportMapFragment
        mapFragment.getMapAsync {
            maps = it
        }
    }
}