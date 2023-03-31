package ClassFiles

import com.google.firebase.Timestamp
import java.util.*

class Ticket {

    var priority = 0
    var date_time: Timestamp? = null
    var exitTime: Timestamp? = null
    var endServeTime: Timestamp? = null
    var total_process_time: Timestamp? = null
    var total_waited_time: Timestamp? = null // TODO: Fix format
    var processType = ""
    var served_employee =""
    var customer_id = ""
    var name = ""
    var surname = ""
    var result = ""

    // Constructor with name and surname
    constructor(priority:Int,  processType:String, customer_id:String, name:String, surname:String){
        this.priority = priority
        this.processType = processType
        this.customer_id = customer_id
        this.date_time = Timestamp.now()
        this.name = name
        this.surname = surname

    }

    constructor(){
        this.processType = "DEFAULT CONSTRUCTOR"
        this.served_employee ="DEFAULT CONSTRUCTOR"
        this.customer_id = "DEFAULT CONSTRUCTOR"

    }


    fun updateExitTime(){
        this.exitTime = Timestamp.now()
    }

    fun updateEndServeTime(){
        this.endServeTime = Timestamp.now()
    }

    fun updateStartTime(){
        this.date_time = Timestamp.now()
    }

    fun calculateWaitTime() {
        if (date_time != null && exitTime != null) {
            val entryDate = date_time!!.toDate()
            val exitDate = exitTime!!.toDate()
            val waitTimeInMillis = exitDate.time - entryDate.time
            this.total_waited_time = Timestamp(Date(waitTimeInMillis))
        } else {
            throw IllegalStateException("Both entryTime and exitTime must be set to calculate wait time")
        }
    }

    fun calculateProcessTime() {
        if (endServeTime != null && exitTime != null) {
            val endServeDate = endServeTime!!.toDate()
            val exitDate = exitTime!!.toDate()
            val processTimeInMillis = endServeDate.time - exitDate.time
            this.total_process_time = Timestamp(Date(processTimeInMillis))
        } else {
            throw IllegalStateException("Both endServeDate and exitTime must be set to calculate wait time")
        }
    }

    fun getterProcessType() : String{
        return this.processType
    }




}