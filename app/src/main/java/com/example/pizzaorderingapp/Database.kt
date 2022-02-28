package com.example.pizzaorderingapp

object Database {
    val listOfItems = arrayListOf<Pizza>(
        Pizza("Tandoori Paneer",R.drawable.tandooripaneer,
        mutableMapOf(Size.Regular to "299",Size.Medium to "499",Size.Large to "699"),Category.Veg),
        Pizza("Tandoori Chicken pizza",R.drawable.tandooripaneer,mutableMapOf(Size.Medium to "599",Size.Large to "799"),Category.NonVeg),
        Pizza("Chicken pizza",R.drawable.tandooripaneer,mutableMapOf(Size.Regular to "599",Size.Large to "799"),Category.NonVeg))
    val listOfToppings = arrayListOf<Topping>(Topping("cheese","50"),Topping("mushrooms","100"))
    val listOfUsers = arrayListOf<Users>(
        Users("Nithya","7904788454","Nithya", arrayListOf(""),false),
        Users("Rosy","9043885171","Rosy", arrayListOf(""),true)
    )
}