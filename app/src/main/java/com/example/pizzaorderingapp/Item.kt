package com.example.pizzaorderingapp

import android.os.Parcelable
import java.io.Serializable

data class Item(
    val item: Pizza,
    var quantity: Int, val toppings: ArrayList<Topping>, val size: Size, var price: Int
) : Serializable