package com.example.pizzaorderingapp

import android.os.Parcelable
import java.io.Serializable

data class Item(
    val itemId: Int,
    val pizzaId: Int,
    var quantity: Int,
    val size: Size,
    var price: Int
)