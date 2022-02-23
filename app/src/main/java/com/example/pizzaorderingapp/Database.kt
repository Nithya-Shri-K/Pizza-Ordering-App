package com.example.pizzaorderingapp

object Database {
    val listOfItems = arrayListOf<Item>(
        Item("Tandoori Paneer",R.drawable.tandooripaneer,
        mutableMapOf(Size.Regular to "299",Size.Medium to "499",Size.Large to "699"),Category.Veg),
        Item("Tandoori Chicken pizza",R.drawable.tandooripaneer,mutableMapOf(Size.Medium to "599",Size.Large to "799"),Category.NonVeg))
    val listOfToppings = ArrayList<Topping>()
    val listOfUsers = arrayListOf<Users>(
        Users("Nithya","7904788454","Nithya", arrayListOf(""),false),
        Users("Rasal","9043885171","Rasal", arrayListOf(""),true)
    )
}