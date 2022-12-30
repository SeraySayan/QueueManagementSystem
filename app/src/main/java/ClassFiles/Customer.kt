package ClassFiles

import android.webkit.WebSettings.RenderPriority

class Customer : User{

    constructor(id: Int,
                name: String,
                surname: String,
                email: String,
                password: String,
                reg_date: String,
                phone_no: String,
                address: String
    ) : super(id,name,surname,email,password,reg_date,phone_no,address){

    }

    constructor(){
        id = 0
        name = ""
        surname = ""
        email = ""
        password = ""// TODO: make this type "Date" class.
        reg_date = ""
        phone_no = ""
        address = ""
    }

    constructor(id:Int){ // DEBUG Purposes
        this.id = id
    }


}