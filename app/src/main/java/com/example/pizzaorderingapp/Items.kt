package com.example.pizzaorderingapp

import java.io.Serializable

sealed class Items: Serializable{
    data class Pizza(var name : String, var image : Int, var sizeAndPrice : MutableMap<Size,String>, var category : Category, var id : Int = ++idCreator) : Items(),Serializable{
        companion object{
            var idCreator = 0
        }
    }
    data class Topping(var name : String, var price : String,val id : Int = ++idCreator ) : Items(){
        companion object{
            var idCreator = 0
        }
    }
}
