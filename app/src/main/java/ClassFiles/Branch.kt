package ClassFiles


data class Branch(
    val name:String?=null,
    val location:Float?=null,
    val Queue:String?=null
    //val id:Int?=null
    )
// arraylist kullanınca deserialization hatası çıkıyor bazı durumlarda
// o yuzden suanlık arraylist kullanımını kaldırdım