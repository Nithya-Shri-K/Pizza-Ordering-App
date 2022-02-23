package com.example.pizzaorderingapp

object AdminHandler {

    fun addItem(name : String,image : Int,sizeAndPrice : MutableMap<Size,String>,category : Category){
        val item = Item(name,image,sizeAndPrice,category)
        Database.listOfItems.add(item)

    }
    fun updateItem(item: Item,name : String,image : Int,sizeAndPrice : MutableMap<Size,String>,category : Category){
       item.name=name
        item.image = image
        item.category=category
        item.sizeAndPrice =sizeAndPrice
    }
    fun removeItem(id : Int)
    {
        val itemToRemove : Item = Database.listOfItems.filter { it.id == id }[0]
        Database.listOfItems.remove(itemToRemove)
    }
    fun addTopping(id : String,name : String,price : Float){
        val topping = Topping(id,name,price)
        Database.listOfToppings.add(topping)
    }
    fun removeTopping(id : String){
        val toppingToRemove : Topping = Database.listOfToppings.filter{ it.id == id }[0]
        Database.listOfToppings.remove(toppingToRemove)
    }
    fun showOrders(){

    }
    fun dashboard(){

    }
}