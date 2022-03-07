package com.example.pizzaorderingapp

object CartItemsHandler {
    fun createItem(selectedItem : Pizza,quantity : Int,selectedToppings : ArrayList<Topping>,selectedSize : Size,totalPrice : Int) : Item{
        return Item(selectedItem,quantity,selectedToppings,selectedSize,totalPrice)
    }
}