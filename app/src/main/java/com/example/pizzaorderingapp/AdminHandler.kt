package com.example.pizzaorderingapp

object AdminHandler {

    fun addItem(name : String,image : Int,sizeAndPrice : MutableMap<Size,String>,category : Category){
        val item = Items.Pizza(name,image,sizeAndPrice,category)
        Database.listOfItems.add(item)

    }
    fun updateItem(pizza: Items.Pizza, name : String, image : Int, sizeAndPrice : MutableMap<Size,String>, category : Category){
       pizza.name=name
        pizza.image = image
        pizza.category=category
        pizza.sizeAndPrice =sizeAndPrice
    }
    fun updateTopping(topping : Items.Topping, name : String, price : String){
        topping.name = name
        topping.price = price
    }
    fun removeItem(id : Int)
    {
        val pizzaToRemove : Items = Database.listOfItems.filter { item -> item.id == id }[0]
        println(pizzaToRemove)
        Database.listOfItems.remove(pizzaToRemove)
    }
    fun addTopping(name : String,price : String){
        val topping = Items.Topping(name,price)
        Database.listOfToppings.add(topping)
    }
    fun removeTopping(id : Int){
        val toppingToRemove : Items = Database.listOfToppings.filter{item -> item.id == id }[0]
        Database.listOfToppings.remove(toppingToRemove)
    }
    fun showOrders(){

    }
    fun dashboard(){

    }
}