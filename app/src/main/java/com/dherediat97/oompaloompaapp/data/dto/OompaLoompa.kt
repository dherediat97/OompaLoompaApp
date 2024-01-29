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
    val description: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum",
    val email: String,
    val age: Int,
    val country: String,
    val favorite: Favorite,
    val height: Int
)

data class Favorite(
    val color: String,
    val food: String,
    val song: String
)