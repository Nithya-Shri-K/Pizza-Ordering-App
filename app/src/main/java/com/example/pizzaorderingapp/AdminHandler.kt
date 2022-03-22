package com.example.pizzaorderingapp

import android.content.Context

object AdminHandler {

    fun addPizza(
        name: String,
        image: Int,
        category: Category,
        sizeAndPrice: MutableMap<Size, String>,
        context: Context
    ) {
        DatabaseHelper(context).insertPizza(name, image, category, sizeAndPrice)
    }

    fun updatePizza(
        pizzaId: Int,
        name: String,
        image: Int,
        sizeAndPrice: MutableMap<Size, String>,
        category: Category, context: Context
    ) {

        DatabaseHelper(context).updatePizza(pizzaId, name, image, category, sizeAndPrice)
    }


    fun removePizza(id: Int, context: Context) {
        DatabaseHelper(context).deletePizza(id)
    }

    fun updateTopping(toppingId: Int, name: String, price: Int, databaseHelper: DatabaseHelper) {
        databaseHelper.updateTopping(toppingId, name, price)
    }

    fun addTopping(name: String, price: Int, databaseHelper: DatabaseHelper) {
        databaseHelper.insertTopping(name, price)
    }

    fun removeTopping(id: Int, databaseHelper: DatabaseHelper) {
        databaseHelper.deleteTopping(id)
    }


}