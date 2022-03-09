package com.example.pizzaorderingapp

import java.io.Serializable

data class User(
    val name: String,
    val phoneNumber: String,
    val password: String,
    val address: ArrayList<Address>,
    val cart: ArrayList<Item>,
    val isAdmin: Boolean,
    val id: Int = ++idCreator
) : Serializable {
    companion object {
        var idCreator = 0
    }
}
