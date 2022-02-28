package com.example.pizzaorderingapp

data class Topping(var name : String, var price : String,val id : Int = ++idCreator ){
    companion object{
        var idCreator = 0
    }
}