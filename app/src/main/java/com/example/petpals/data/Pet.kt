package com.example.petpals.data

data class Pet(
       var id: Int,
       val name: String,
       val species: String,
       val breed: String?,
       val age: Int?,
       val gender: String,
       val description: String?,
       val imageUrl: String?,
       val uploadDate: Long?,
       val location: String?
) {
       // No-argument constructor required by Firebase
       constructor() : this(-1, "","","",null,"","", "", null,"")
}
