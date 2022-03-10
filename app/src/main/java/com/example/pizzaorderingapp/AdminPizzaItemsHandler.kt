package com.example.pizzaorderingapp

interface AdminPizzaItemsHandler {
    fun refresh()
    fun edit(pizza: Pizza)
}