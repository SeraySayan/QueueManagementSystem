package com.example.queuemanagement

import ClassFiles.Ticket
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.queuemanagement.databinding.ActivityEmployeeQueueBinding
import com.google.firebase.firestore.FirebaseFirestore

class EmployeeQueue : AppCompatActivity() {
    lateinit var binding: ActivityEmployeeQueueBinding
    private lateinit var queueList:ArrayList<Ticket>
    val database = FirestoreDB()

    private var db= FirebaseFirestore.getInstance() // TODO: implement custom class
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeQueueBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db= FirebaseFirestore.getInstance()

////
        val eQueue="/Queue/queue1/TicketsInQueue"
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        //queueList = arrayListOf()
        //TODO: databaseden hangi queue olduğunu çek
        database.listenToChanges(eQueue){ querySnapshot ->
            var myList = mutableListOf<Ticket>()
            var queueList = mutableListOf<Ticket>()
            database.getQueueTEST(eQueue) { data ->
                myList = data

                for (x in myList){
                    //queueList.add( x.id.toString() + " " + x.processType + " " + x.total_waited_time.toString() + "\n"   )
                    queueList.add(x)
                }
                //TODO:if there is no one, add the first one; if there is, wait
                binding.textView1.text = "Name: "+queueList[0].customer_id
                binding.textView2.text = "Surname: "+queueList[0].customer_id
                binding.textView3.text = "Process Type: "+queueList[0].processType
                binding.textView4.text = "Duration: "+queueList[0].total_waited_time//change it to duration
                queueList.removeAt(0)//databaseden de güncelle

                val adapter = QueueAdapter(queueList, this)
                binding.recyclerView.adapter = adapter

            }
        }
       // database.dequeue(eQueue)

       /* db.collection("/Queue/queue1/TicketsInQueue").get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    for (data in it.documents){
                        val ticket: Ticket? =data.toObject(Ticket::class.java)
                        queueList.add(ticket!!)
                    }
                    val adapter = QueueAdapter(queueList, this)
                    binding.recyclerView.adapter = adapter

                }
            }
            .addOnFailureListener{
                Toast.makeText(this,it.toString(), Toast.LENGTH_SHORT).show()
            }*/
    }
}


