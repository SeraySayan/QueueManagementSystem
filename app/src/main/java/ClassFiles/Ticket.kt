package ClassFiles

import com.google.firebase.firestore.FieldValue
import java.util.Date

class Ticket {

    var id = 1
    var priority = 0
    var date_time : FieldValue // Timestamp for Firestore
    var status = false  // For easy implement for dequeue() or LeaveQueue()
    var processType = ""
    var total_waited_time = 0
    var served_employee =""
    var customer_id = 0

    // Constructor for creation from customer
    constructor(id:Int, priority:Int,  processType:String, total_waited_time: Int, customer_id:Int ){
        this.id = id
        this.priority = priority
        this.processType = processType
        this.customer_id = customer_id
        this.total_waited_time= total_waited_time
        this.date_time = FieldValue.serverTimestamp()
        this.status = true // new created tickets will be active

    }

    constructor(id:Int, priority:Int,  processType:String, customer_id:Int ){
        this.id = id
        this.priority = priority
        this.processType = processType
        this.customer_id = customer_id
        this.date_time = FieldValue.serverTimestamp()
        this.status = true // new created tickets will be active

    }

    constructor(){
        this.id = -1
        this.date_time = FieldValue.serverTimestamp()
        this.status = false
        this.processType = ""
        this.total_waited_time = 0
        this.served_employee =""
        this.customer_id = 0

    }

    fun LeaveQueue(){   // TODO
        println("You used LeaveQueue")
    }




}