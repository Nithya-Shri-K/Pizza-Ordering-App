package com.example.pizzaorderingapp

interface ToppingSelector {
    fun onToppingSelected(topping: Topping)
    fun onToppingDeselected(topping: Topping)
}