package ClassFiles

class Employee : User {

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
    var counter : Counter = Counter()

}
