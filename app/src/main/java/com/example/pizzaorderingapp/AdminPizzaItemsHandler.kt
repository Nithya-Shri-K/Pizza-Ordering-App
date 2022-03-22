package com.example.pizzaorderingapp

interface AdminPizzaItemsHandler {
    fun refreshPizzaList()
    fun editPizza(pizza: Pizza)
}