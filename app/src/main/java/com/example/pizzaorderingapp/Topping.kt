package com.example.pizzaorderingapp

import java.io.Serializable

data class Topping(var name : String, var price : String,val id : Int = ++idCreator ) : Serializable{
    companion object{
        var idCreator = 0
    }
}