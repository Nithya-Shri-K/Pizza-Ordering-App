package com.example.pizzaorderingapp

import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

data class Order(
    val items: ArrayList<Item>,
    val totalPrice: Int,
    var status: Status,
    val date: String,
    val userId: Int,
    val deliveryAddress: String,
    val orderId: Int = ++idCreator
) {
    companion object {
        var idCreator = 0
    }
}