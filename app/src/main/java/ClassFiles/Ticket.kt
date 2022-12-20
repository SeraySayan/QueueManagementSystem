package ClassFiles

import java.util.Date

class Ticket {

    var id = 1
    var date_time = "" //TODO: make this type "Date" class.
    var status = false  // For easy implement for dequeue() or LeaveQueue()
    var processType = ""
    var total_waited_time = 0
    var served_employee =""
    var customer_id = 0

    // Constructor for creation from customer
    constructor(id:Int,  processType:String,  customer_id:Int ){
        this.id = id
        this.processType = processType
        this.customer_id = customer_id

        this.date_time = "TEST" // will be initialized auto by current system date time (no user input)
        this.status = true // new created tickets will be active

    }

    constructor(){
        this.id = -1
        this.date_time = ""
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