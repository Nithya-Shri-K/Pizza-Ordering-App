package com.example.pizzaorderingapp

interface AdminToppingHandler {
    fun refreshToppingList()
    fun editTopping(topping: Topping)
}