package com.example.pizzaorderingapp

import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

data class Order(
    val orderId: Int,
    val userId: Int,
    val totalPrice: Int,
    var status: Status,
    val date: String,
    val deliveryAddress: Int
)
