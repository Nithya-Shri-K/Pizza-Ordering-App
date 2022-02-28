package com.example.pizzaorderingapp

interface UserActionListener {
    fun customize(selectedItem : Pizza,size : String,price:String)
}