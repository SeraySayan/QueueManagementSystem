package ClassFiles

import com.google.firebase.Timestamp
import java.time.Duration
import java.time.Instant

class Ticket {

    var priority = 0
    var date_time: Timestamp? = null // EntryTime to queue
    var exitTime: Timestamp? = null
    var endServeTime: Timestamp? = null
    var total_process_time: String? = null
    var total_waited_time: String? = null // TODO: Fix format
    var processType = ""
    var served_employee =""
    var branch_name = ""
    var customer_id = ""
    var name = ""
    var surname = ""
    var result = ""

    // Constructor with name and surname
    constructor(
        priority: Int,
        processType: String,
        branch: String,
        customer_id: String,
        name: String,
        surname: String
    ){
        this.priority = priority
        this.processType = processType
        this.branch_name = branch
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

            this.total_waited_time =  formatTimestamp(exitTime!!, date_time!!)


        } else {
            throw IllegalStateException("Both entryTime and exitTime must be set to calculate wait time")
        }
    }

    fun calculateProcessTime() {
        if (endServeTime != null && exitTime != null) {

            this.total_process_time =  formatTimestamp(endServeTime!!, exitTime!!)
        } else {
            throw IllegalStateException("Both endServeDate and exitTime must be set to calculate wait time")
        }
    }

    fun getterProcessType() : String{
        return this.processType
    }


}

fun formatDuration(duration: Duration): String {
    val minutes = duration.toMinutes()
    val seconds = duration.minusMinutes(minutes).seconds
    val builder = StringBuilder()
    if (minutes > 0) {
        builder.append("$minutes" + "m ")
    }
    builder.append("$seconds" + "s")
    return builder.toString()
}


fun formatTimestamp(timestamp1: Timestamp, timestamp2: Timestamp): String {
    val instant1 = Instant.ofEpochSecond(timestamp1.seconds)
    val instant2 = Instant.ofEpochSecond(timestamp2.seconds)
    val duration = Duration.between(instant2, instant1)
    return formatDuration(duration)
}