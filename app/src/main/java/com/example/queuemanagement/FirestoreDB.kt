package com.example.queuemanagement

import ClassFiles.*
import android.app.Activity

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/**
 * Trying to implement Firestore in this class.
 * That way, we can reach DB with only one class
 * for every file and screen.
 * There is a problem with listeners, and I'm
 * working on that rn.
 * */


class FirestoreDB  {

    private var db: FirebaseFirestore = Firebase.firestore

    // Sample Read data function.

    fun getData(collection: String, callback: (MutableList<Any>) -> Unit) {
        val dataList = mutableListOf<Any>()
        db.collection(collection)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val data = document.data
                    dataList.add(data)
                }
                callback(dataList)
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "Error getting documents: ", exception)
            }
    }

    // Method for adding data to Firestore. Can handle any type of data / object
    fun addData(collection: String, data: Any) {
        db.collection(collection)
            .add(data)
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error adding document", e)
            }
    }

    fun listenToChanges(collection: String, callback: (QuerySnapshot) -> Unit) {
        db.collection(collection)
            .addSnapshotListener { querySnapshot, e ->
                if (e != null) {
                    Log.w("TAG", "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (querySnapshot != null) {
                    callback(querySnapshot)
                } else {
                    Log.d("TAG", "Current data: null")
                }
            }
    }


    fun getDocumentId(documentReference: DocumentReference): String {
        return documentReference.id
    }


}


/*

The listenToChanges method adds a snapshot listener to the specified
Firestore collection. The listener will be triggered whenever
there are changes to the collection, such as when a document is added,
modified, or deleted.

The listener passes the updated QuerySnapshot to a callback
function, which you can use to update your UI or perform other actions.

To use the FirestoreHandler and the listenToChanges method,
you can do something like this:


val firestoreHandler = FirestoreHandler(context)

firestoreHandler.listenToChanges("myCollection") { querySnapshot ->
    // Update UI or perform other actions here
}

This will add a snapshot listener to the "myCollection" collection,
and the callback function will be called whenever there are changes to the collection.

I hope this helps! Let me know if you have any questions.

*/

/* HOW TO USE FUNCTIONS?

database.getData("Customers") { data ->
            // Use the data here
            // Example:
            textView.setText(data.toString())
        }


var c2 = Customer(2453223,"Barış","Gökmen",
                  "baris.gokmen@metu.edu.tr","za123xd",
                  "20/12/2022","+905338745434","Antalya")

database.addData("Customers",c2)

OR

var testTicket = Ticket()
database.addData("/Queue/queue1/TicketsInQueue", testTicket)



database.listenToChanges("Customers") { querySnapshot ->

            database.getData("Customers") { data ->

                myList = data
                textView.setText(myList.toString())
            }
        }

*/



