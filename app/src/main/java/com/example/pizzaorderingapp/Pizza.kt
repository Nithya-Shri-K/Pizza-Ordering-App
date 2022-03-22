package com.example.pizzaorderingapp

import java.io.Serializable
import java.util.HashMap

data class Pizza(
    var name: String,
    var image: Int,
    var sizeAndPrice: MutableMap<Size, String>,
    var category: Category,
    var id: Int
)