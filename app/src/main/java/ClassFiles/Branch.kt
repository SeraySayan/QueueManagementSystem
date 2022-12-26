package ClassFiles

class Branch {

    var name: String = ""
    var id: String = "0"
    var location: Array<Double> = arrayOf(0.0,0.0)
    var branchQueue: Queue = Queue(ArrayList() ,Employee())


    constructor() // sildiğim zaman çalışmıyor, neden bilmiyorum xd
    constructor(id: String, location: Array<Double>){
        this.id = id
        this.location = location
    }

}
