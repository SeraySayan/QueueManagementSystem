package ClassFiles

import android.webkit.WebSettings.RenderPriority

class Customer : User{

     var priority: Int=0

    constructor(
        uid: String,
        name: String,
        surname: String,
        email: String,
        password: String,
        reg_date: String,
        age: Int,
        priority: Int
        ): super(uid,name,surname,email,password,reg_date,age) {
        this.priority=priority

    }

    constructor(){
        uid = ""
        name = ""
        surname = ""
        email = ""
        password = ""// TODO: make this type "Date" class.
        reg_date = ""
        age = 0
    }

    constructor(id:String){ // DEBUG Purposes
        this.uid = id
    }


}