package com.example.pizzaorderingapp

import java.io.Serializable

data class Users(val name : String, val phoneNumber : String, val password : String,val address : ArrayList<String>,val isAdmin : Boolean,val id : Int= ++idCreator) : Serializable
{
    companion object{
        var idCreator =0
    }
}
