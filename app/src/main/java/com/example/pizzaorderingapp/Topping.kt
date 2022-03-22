package com.example.pizzaorderingapp

import java.io.Serializable

data class Topping(var name: String, var price: Int, val id: Int) : Serializable

