package ClassFiles

import com.google.firebase.firestore.GeoPoint


data class Branch(
    val name:String?=null,
    val location:GeoPoint?=null,
    val Queue:String?=null,
    var distance:Float?=null
    //val id:Int?=null
    )
// arraylist kullanınca deserialization hatası çıkıyor bazı durumlarda
// o yuzden suanlık arraylist kullanımını kaldırdım