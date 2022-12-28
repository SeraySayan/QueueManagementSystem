package com.example.queuemanagement

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData

//This is a class that extends LiveData and is used to track the connectivity status of the device.
// It listens for changes in the device's network connectivity and updates its value accordingly.
//
//The ConnectionLive class has two constructors.
// The first one takes a ConnectivityManager object as an argument and stores it in a field.
// The second constructor takes an Application object and uses it to get the ConnectivityManager system service.
//
//The ConnectionLive class also defines a networkCallback object, which is an instance of ConnectivityManager.NetworkCallback.
// This callback listens for changes in the device's network connectivity and posts a value of true or false to
// the LiveData object depending on the availability of a network.
//
//The onActive and onInactive methods are overridden to register and unregister the networkCallback object, respectively.
// This ensures that the networkCallback is only active when the ConnectionLive object has active observers.
//
//The ConnectionLive class requires the device to be running Android Lollipop (API level 21) or higher,
// as indicated by the @RequiresApi(Build.VERSION_CODES.LOLLIPOP) annotations.

class ConnectionLive (private val connectivityManager: ConnectivityManager) :LiveData<Boolean>(){

    constructor(application: Application): this(
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    )

    private val networkCallback = @RequiresApi (Build.VERSION_CODES.LOLLIPOP)
    object: ConnectivityManager.NetworkCallback(){
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            postValue(true)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            postValue(false)
        }
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)

    override fun onActive() {
        super.onActive()
        val builder  = NetworkRequest.Builder()
        connectivityManager.registerNetworkCallback(builder.build(),networkCallback)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }


}