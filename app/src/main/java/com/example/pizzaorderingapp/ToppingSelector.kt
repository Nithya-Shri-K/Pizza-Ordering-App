package com.example.pizzaorderingapp

interface ToppingSelector {
    fun onCheck(topping: Topping)
    fun onUncheck(topping: Topping)
}