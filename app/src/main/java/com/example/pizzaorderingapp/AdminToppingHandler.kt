package com.example.pizzaorderingapp

interface AdminToppingHandler {
    fun refresh()
    fun edit(topping : Topping)
}