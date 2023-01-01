package ClassFiles


// Test for github

class Admin : User {

    private var sa = 0


    constructor(id: String,
                name: String,
                surname: String,
                email: String,
                password: String,
                reg_date: String,
                age: Int

    ) : super(id,name,surname,email,password,reg_date,age){

    }

    constructor(){
        id = ""
        name = ""
        surname = ""
        email = ""
        password = ""// TODO: make this type "Date" class.
        reg_date = ""
        age = 0

    }


    fun BlockCustomer(customer:Customer){
        // TODO
    }
    fun UnblockCustomer(custmer:Customer){
        //TODO
    }


}