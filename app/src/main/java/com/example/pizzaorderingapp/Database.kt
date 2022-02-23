package com.example.pizzaorderingapp

object Database {
    val listOfItems = arrayListOf<Item>(
        Item("Tandoori Paneer",R.drawable.tandooripaneer,
        mutableMapOf(Size.Regular to "299",Size.Medium to "499",Size.Large to "699"),Category.Veg),
        Item("Tandoori Chicken pizza",R.drawable.tandooripaneer,mutableMapOf(Size.Medium to "599",Size.Large to "799"),Category.NonVeg))
    val listOfToppings = ArrayList<Topping>()
    val listOfUsers = arrayListOf<Users>(
        Users("1","Nithya","567889987","Nithya", arrayListOf(""),false),
        Users("2","Rasal","345678989","Rasal", arrayListOf(""),true)
    )
}