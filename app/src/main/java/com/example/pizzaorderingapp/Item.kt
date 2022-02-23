package com.example.pizzaorderingapp

import java.util.HashMap

data class Item(var name : String, var image : Int, var sizeAndPrice : MutableMap<Size,String>, var category : Category,var id : Int = ++idCreator){
    companion object{
        var idCreator = 0
    }
}