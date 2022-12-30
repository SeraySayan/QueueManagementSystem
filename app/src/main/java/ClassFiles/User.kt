package ClassFiles

abstract class User {

    var id: Int = 0
    var name: String = ""
    var surname: String = ""
    var email: String = ""
    var password: String = ""
    var reg_date: String = "" // TODO: make this type "Date" class. // can be done in 2nd term


    constructor() // sildiğim zaman çalışmıyor, neden bilmiyorum xd

    constructor(id: Int, name: String, surname: String, email: String, password: String, regDate: String){
        this.id = id
        this.name = name
        this.surname = surname
        this.email = email
        this.password = password
        this.reg_date = regDate

    }

    open fun getterName():String{
        return this.name
    }

    open fun getterID(): Int {
        return this.id
    }

}