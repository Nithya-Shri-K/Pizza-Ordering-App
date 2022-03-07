package com.example.pizzaorderingapp

interface SelectedTopping {
    fun onCheck(topping : Topping)
    fun onUncheck(topping: Topping)
}