package com.example.pizzaorderingapp

import java.io.Serializable

data class User(
    val userId:Int,
    val name: String,
    val phoneNumber: String,
    val password: String,
    val isAdmin: Int
) : Serializable
