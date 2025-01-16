package com.example.petpals.data

data class Pet(
       val id: Int = -1, // Default value for ID when not provided
       val name: String,
       val species: String,
       val breed: String?,
       val age: Int,
       val gender: String,
       val description: String?,
       var imageUrl: String,
       val uploadDate: Long?,
       val location: String?
) {
       // No-argument constructor required by Firebase
       constructor() : this(-1,"","","",-1,"","", "", null,"")
}
