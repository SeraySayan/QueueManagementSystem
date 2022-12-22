package ClassFiles

import com.google.firebase.firestore.GeoPoint

class Branch {

    var id: Int = 0
    var location: Array<Double> = arrayOf(0.0,0.0)
    //var counter_list: List<Counter>?


    constructor() // sildiğim zaman çalışmıyor, neden bilmiyorum xd
    constructor(id: Int, location: Array<Double>){
        this.id = id
        this.location = location
    }

}
