package com.dherediat97.oompaloompaapp.domain.dto

import com.google.gson.annotations.SerializedName

data class OompaLoompa(
    val id: Int,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val gender: Gender,
    val image: String,
    val profession: String,
    val email: String,
    val age: Int,
    val country: String,
    val favorite: Favorite,
    val height: Int
)
enum class Gender(val value: String){
    F("Female"),
    M("Male")
}

data class Favorite(
    val color: String,
    val food: String,
)