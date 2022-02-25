package com.example.pizzaorderingapp

interface AdminHandlerListener {
    fun refresh()
    fun editPizza(pizza : Items.Pizza)
    fun editTopping(topping : Items.Topping)
}