package com.example.pizzaorderingapp

interface ActionListener {
    fun customize(selectedItem : Items.Pizza,size : String,price:String)
}