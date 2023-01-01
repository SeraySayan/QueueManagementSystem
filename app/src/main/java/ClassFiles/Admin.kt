package ClassFiles


// Test for github

class Admin : User {

    private var sa = 0


    constructor(id: Int,
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


    fun BlockCustomer(customer:Customer){
        // TODO
    }
    fun UnblockCustomer(custmer:Customer){
        //TODO
    }


}