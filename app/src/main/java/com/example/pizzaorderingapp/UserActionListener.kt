package com.example.pizzaorderingapp

interface UserActionListener {
    fun startCustomizeFragment(selectedItem : Pizza, size : Size, price :Int)
}