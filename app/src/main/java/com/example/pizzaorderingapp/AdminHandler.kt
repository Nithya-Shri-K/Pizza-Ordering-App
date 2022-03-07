package com.example.pizzaorderingapp

object AdminHandler {

    fun addItem(name : String,image : Int,sizeAndPrice : MutableMap<Size,String>,category : Category){
        val item = Pizza(name,image,sizeAndPrice,category)
        Database.listOfItems.add(item)

    }
    fun updateItem(pizza: Pizza, name : String, image : Int, sizeAndPrice : MutableMap<Size,String>, category : Category){
       pizza.name=name
        pizza.image = image
        pizza.category=category
        pizza.sizeAndPrice =sizeAndPrice

    }
    fun updateTopping(topping :Topping, name : String, price : String){
        topping.name = name
        topping.price = price
    }
    fun removeItem(id : Int)
    {
        val pizzaToRemove : Pizza = Database.listOfItems.filter { item -> item.id == id }[0]
        Database.listOfItems.remove(pizzaToRemove)
    }
    fun addTopping(name : String,price : String){
        val topping = Topping(name,price)
        Database.listOfToppings.add(topping)
    }
    fun removeTopping(id : Int){
        val toppingToRemove : Topping = Database.listOfToppings.filter{item -> item.id == id }[0]
        Database.listOfToppings.remove(toppingToRemove)
    }
    fun showOrders(){

    }
    fun dashboard(){

    }
}