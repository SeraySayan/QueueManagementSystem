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
    var customer_id = ""

    // Constructor for creation from customer
    constructor(id:Int, priority:Int,  processType:String, customer_id:String ){
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
        this.customer_id = ""

    }

    fun LeaveQueue(){   // TODO
        println("You used LeaveQueue")
    }

    fun getterProcessType() : String{
        return this.processType
    }




}