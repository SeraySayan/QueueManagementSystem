package ClassFiles

class Customer : User{

    constructor(
        id: Int,
        name: String,
        surname: String,
        email: String,
        password: String,
        reg_date: String,

        ) : super(id,name,surname,email,password,reg_date){

    }

    constructor(){
        id = 0
        name = ""
        surname = ""
        email = ""
        password = ""// TODO: make this type "Date" class.
        reg_date = ""

    }

    constructor(id:Int){ // DEBUG Purposes
        this.id = id
    }

    fun createTicket(priority: Int, processType:String): Ticket{ // TODO

        return Ticket(1,priority,processType,this.id)
    }

}