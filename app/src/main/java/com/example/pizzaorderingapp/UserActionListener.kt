package com.example.pizzaorderingapp

interface UserActionListener {
    fun startCustomizeFragment(selectedPizzaId: Int, size: Size, price: Int)
}