package com.example.queuemanagement


import ClassFiles.*
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.sql.Timestamp

class Data {
    private val preTimestamp = Timestamp(System.currentTimeMillis())
    private val emp1=Employee()
    private val emp2=Employee()
    private val ticket1= Ticket()
    private val myTickets1= ArrayList<Ticket>()
    private val ticket2= Ticket()
    private val myTickets2= ArrayList<Ticket>()



    val queue1 = Queue(myTickets1,emp1)
    val queue2 = Queue(myTickets2,emp2)
    val myQueues= ArrayList<Queue>()
    fun func1(){
        myTickets1.add(ticket1)
        myTickets2.add(ticket2)
    }
    fun func2(){
        myQueues.add(queue1)
        myQueues.add(queue2)
    }

}