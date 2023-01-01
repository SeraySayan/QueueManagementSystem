package ClassFiles

abstract class User {

    var id: String = ""
    var name: String = ""
    var surname: String = ""
    var email: String = ""
    var password: String = ""
    var reg_date: String = "" // TODO: make this type "Date" class. // can be done in 2nd term
    var age: Int  = 0

    constructor() // sildiğim zaman çalışmıyor, neden bilmiyorum xd

    constructor(id: String, name: String, surname: String, email: String, password: String, regDate: String, age: Int){
        this.id = id
        this.name = name
        this.surname = surname
        this.email = email
        this.password = password
        this.reg_date = regDate
        this.age = age

    }

    open fun getterName():String{
        return this.name
    }

    open fun getterID(): String {
        return this.id
    }

}