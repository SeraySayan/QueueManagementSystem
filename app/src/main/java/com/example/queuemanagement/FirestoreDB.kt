package com.example.queuemanagement

import ClassFiles.Ticket
import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.*
import com.google.firebase.firestore.DocumentSnapshot


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

    var map: HashMap<String, Int> = hashMapOf("Withdraw/Deposit Money" to 20,
        "Payments" to 25,
        "Investment to Currency" to 30,
        "Open New Account" to 40)


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

    // This function will take the collection, field and its value. Then finds the correct
    // document and return its Document Snapshot.
    fun getDocumentByField(collectionName: String, fieldName: String, fieldValue: String?, callback: (DocumentSnapshot?) -> Unit) {
        val query = db.collection(collectionName).whereEqualTo(fieldName, fieldValue)
        val task= query.get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    callback(documents.first())
                }
                else {
                    Log.d("getDocumentSnapshot", "Success but query is null")
                    callback(null)
                }
            }
            .addOnFailureListener { exception ->
                Log.d("getDocumentSnapshot", "FAILED", exception)
                callback(null)
            }
    }


    @SuppressLint("SuspiciousIndentation")
    fun getQueueActive2(collection: String, uid : String,priority : Int, callback: (MutableList<Int>, MutableList<Int>) -> Unit) {
    //according to priority,correct time calculation for active queue page
        val CurrentIndex = mutableListOf<Int>()
        var WaitingTime = mutableListOf<Int>()
        CurrentIndex.add(0)
        db.collection(collection).orderBy("priority",Query.Direction.DESCENDING).orderBy("date_time")
            .get()
            .addOnSuccessListener { result ->

                for (document in result) {
                    if(document.data["priority"].toString().toInt() >= priority){
                        if(document.data["customer_id"].toString().equals(uid)){
                            CurrentIndex[0] = (result.indexOf(document)+1)
                            break
                        }
                        else{
                            WaitingTime.add(map[document.get("processType")]!!)
                        }
                    }

                }
                callback(CurrentIndex, WaitingTime)
            }
            .addOnFailureListener { exception ->
                Log.d("getQueue", "Error getting documents: ", exception)
            }
    }

    fun getTransactionQueue(collection: String,priority : Int, callback: (MutableList<Int>, MutableList<Int>) -> Unit) {
        //according to priority, time and size calculation to show in transaction page
        var WaitingTime = mutableListOf<Int>()
        var queueSize= mutableListOf<Int>()
        db.collection(collection).orderBy("priority",Query.Direction.DESCENDING).orderBy("date_time")
            .get()
            .addOnSuccessListener { result ->

                for (document in result) {
                    if(document.data["priority"].toString().toInt() >= priority){
                            WaitingTime.add(map[document.get("processType")]!!)
                            queueSize.add(1)
                    }

                }
                callback(queueSize, WaitingTime)
            }
            .addOnFailureListener { exception ->
                Log.d("getQueue", "Error getting documents: ", exception)
            }
    }
    // An alternative version, for callback "Ticket" objects.
    fun getQueueTEST(collection: String, callback: (MutableList<Ticket>) -> Unit) {
        val dataList = mutableListOf<Ticket>()
        db.collection(collection).orderBy("priority",Query.Direction.DESCENDING).orderBy("date_time")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    // This formatting is temp. //TODO: Implement a "documentToObject" method.
                    var myticket = Ticket(
                        document.data["priority"].toString().toInt(),
                        document.data["processType"].toString(),
                        document.data["branch_name"].toString(),
                        document.data["customer_id"].toString(),
                        document.data["name"].toString(),
                        document.data["surname"].toString()
                    )
                    dataList.add(myticket)
                }
                callback(dataList)
            }
            .addOnFailureListener { exception ->
                Log.d("getQueue", "Error getting documents: ", exception)
            }
    }


    // This function returns the Ticket on the top of the queue.
    fun getNextCustomer(collection: String, callback: (MutableList<Ticket>) -> Unit){

        var returnTicket = mutableListOf<Ticket>()

        db.collection(collection)
            .orderBy("priority",Query.Direction.DESCENDING)
            .orderBy("date_time").limit(1).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    returnTicket.add(ticketFromFirestore(document))
                }
                callback(returnTicket)
            }
            .addOnFailureListener { exception ->
                Log.d("getNextCustomer", "Error ", exception)
            }


    }


    // This method copies the first index of the input queue
    // to "Tickets" collection. Then, deletes the ticket from the queue
    fun dequeue(collection: String) {

        db.collection(collection)
            .orderBy("priority",Query.Direction.DESCENDING)
            .orderBy("date_time").limit(1).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    document.reference.delete()     // Deletes the real ticket from the queue
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Dequeue", "Error ", exception)
            }
    }

    // Same as dequeue, but instead of indexed query, it directly
    // searches for input ticket_id and deletes that ticket
    fun leaveQueue(collection: String, uid : String){
        db.collection(collection).whereEqualTo("customer_id",uid).limit(1).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    saveTicket(document)       // Copies the ticket data to "Tickets" collection
                    document.reference.delete()     // Deletes the real ticket from the queue
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Dequeue", "Error ", exception)
            }
    }


    // Takes the Data and saves it to the Tickets collection
    fun saveTicket(ticket: DocumentSnapshot){
        var myticket = ticketFromFirestore(ticket)
        myticket.updateExitTime()
        myticket.calculateWaitTime()
        addData("Tickets", myticket)
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

    fun ticketFromFirestore(doc: DocumentSnapshot): Ticket {
        val ticket = Ticket()
        ticket.priority = doc.getLong("priority")?.toInt() ?: 0
        ticket.date_time = doc.getTimestamp("date_time")
        //ticket.exitTime = doc.getTimestamp("exitTime")
        //ticket.total_waited_time = doc.getTimestamp("total_waited_time")
        ticket.processType = doc.getString("processType") ?: ""
        ticket.branch_name = doc.getString("branch_name") ?: ""
        ticket.served_employee = doc.getString("served_employee") ?: ""
        ticket.customer_id = doc.getString("customer_id") ?: ""
        ticket.name = doc.getString("name") ?: ""
        ticket.surname = doc.getString("surname") ?: ""
        return ticket
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



