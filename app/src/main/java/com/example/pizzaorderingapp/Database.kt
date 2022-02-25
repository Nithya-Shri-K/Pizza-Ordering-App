package com.example.pizzaorderingapp

object Database {
    val listOfItems = arrayListOf<Items.Pizza>(
        Items.Pizza("Tandoori Paneer",R.drawable.tandooripaneer,
        mutableMapOf(Size.Regular to "299",Size.Medium to "499",Size.Large to "699"),Category.Veg),
        Items.Pizza("Tandoori Chicken pizza",R.drawable.tandooripaneer,mutableMapOf(Size.Medium to "599",Size.Large to "799"),Category.NonVeg))
    val listOfToppings = arrayListOf<Items.Topping>(Items.Topping("cheese","50"),Items.Topping("mushrooms","100"))
    val listOfUsers = arrayListOf<Users>(
        Users("Nithya","7904788454","Nithya", arrayListOf(""),false),
        Users("Rasal","9043885171","Rasal", arrayListOf(""),true)
    )
}