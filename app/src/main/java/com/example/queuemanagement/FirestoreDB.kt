package com.example.queuemanagement

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.*


/**
 * Trying to implement Firestore in this class.
 * That way, we can reach DB with only one class
 * for every file and screen.
 * There is a problem with listeners, and I'm
 * working on that rn.
 * */


class FirestoreDB  {

    // Getting Firestore object instance
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()


    // getData() will take a collection dir, and returns a MutableList of documents.
    // Using with a listener (listenToChanges()) is recommended
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
                Log.d("getData", "Error getting documents: ", exception)
            }
    }

    /*fun getInfo(collection: String,lookFor: String, callback: (MutableList<Any>) -> Unit) {
        val dataList = mutableListOf<Any>()
        db.collection(collection).whereEqualTo("customer_id",lookFor)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val data = document.data
                    dataList.add(data)
                }
                callback(dataList)
            }
            .addOnFailureListener { exception ->
                Log.d("getData", "Error getting documents: ", exception)
            }
    }*/

    // getting Queue function. Similar to getData(), but this method has
    // the queue query for firestore Index.
    fun getQueue(collection: String, callback: (MutableList<Any>) -> Unit) {
        val dataList = mutableListOf<Any>()
        db.collection(collection).orderBy("priority",Query.Direction.ASCENDING).orderBy("date_time")
            .whereEqualTo("status",true)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    // This formatting is temp. //TODO: Implement a "documentToObject" method.
                    val data = document.data["tickets"].toString()  +  document.data["servedEmp"].toString()
                    dataList.add(data)
                }
                callback(dataList)
            }
            .addOnFailureListener { exception ->
                Log.d("getQueue", "Error getting documents: ", exception)
            }
    }

    fun getQueue2(collection: String,lookFor: String, callback: (MutableList<Any>) -> Unit) {
        val dataList = mutableListOf<Any>()
        db.collection(collection).orderBy("priority",Query.Direction.ASCENDING).orderBy("date_time")
            .whereEqualTo("status",true).whereEqualTo("customer_id",lookFor)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    // This formatting is temp. //TODO: Implement a "documentToObject" method.
                    val data = document.data["tickets"].toString()  +  document.data["servedEmp"].toString()
                    dataList.add(data)
                }
                callback(dataList)
            }
            .addOnFailureListener { exception ->
                Log.d("getQueue", "Error getting documents: ", exception)
            }
    }

    // This method reaches the qeueue Index and delete the
    // first item. NOTE: WILL CHANGE TO NOT DELETE!
    // Instead, we need to move that ticket document to another collection "Tickets"
    // TODO: 2 Different dequeue must be exist. One for customer, one for employee
    // Also I gotta do some more process such as adding details to tickets
    fun dequeue(collection: String) {

        db.collection(collection)
            .orderBy("priority",Query.Direction.DESCENDING)
            .orderBy("date_time").limit(1).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    document.reference.delete() // TODO: DO NOT DELETE ANYTHING!
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Dequeue", "Error ", exception)
            }


    }

    // Method for adding data to Firestore. Can handle any type of data / object
    fun addData(collection: String, data: Any) {
        db.collection(collection)
            .add(data)
            .addOnSuccessListener { documentReference ->
                Log.d("addData", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("addData", "Error adding document", e)
            }
    }

    // Listener of this class. "How to use" in below.
    fun listenToChanges(collection: String, callback: (QuerySnapshot) -> Unit) {
        db.collection(collection)
            .addSnapshotListener { querySnapshot, e ->
                if (e != null) {
                    Log.w("listenToChanges", "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (querySnapshot != null) {
                    callback(querySnapshot)
                } else {
                    Log.d("listenToChanges", "Current data: null")
                }
            }
    }

    // TEST
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


val database = FirestoreDB()

database.listenToChanges("myCollection") { querySnapshot ->
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



