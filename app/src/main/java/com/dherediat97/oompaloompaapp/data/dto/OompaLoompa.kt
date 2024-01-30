package com.dherediat97.oompaloompaapp.data.dto

import com.google.gson.annotations.SerializedName

data class OompaLoompa(
    val id: Int,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val gender: String,
    val image: String,
    val profession: String,
    val description: String = "",
    val email: String,
    val age: Int,
    val country: String,
    val favorite: Favorite,
    val height: Int
)
