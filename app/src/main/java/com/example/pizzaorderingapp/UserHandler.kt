package com.example.pizzaorderingapp

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object UserHandler {

    fun createUser(
        firstname: String,
        lastname: String,
        phoneNumber: String,
        password: String
    ): User {
        val user = User(
            "$firstname $lastname",
            phoneNumber,
            password,
            arrayListOf(),
            arrayListOf(),
            false
        )
        Database.listOfUsers.add(user)
        return user
    }

    fun addToCart(user: User, item: Item) {
        user.cart.add(item)
    }

    fun placeOrder(
        items: ArrayList<Item>,
        totalPrice: Int,
        status: Status,
        userId: Int,
        deliveryAddress: String
    ): Order {
        val listOfItems = arrayListOf<Item>()
        for (item in items) {
            listOfItems.add(item)
        }
        val currentDate = Calendar.getInstance().time
        val myFormat = "dd-MM-yyyy"
        val formattedDate = SimpleDateFormat(myFormat, Locale.UK)
        val selectedDate = formattedDate.format(currentDate)

        val order =
            Order(listOfItems, totalPrice, Status.Waiting, selectedDate, userId, deliveryAddress)
        Database.listOfOrders.add(order)
        clearCart(userId)
        return order

    }

    fun addAddress(currentUser: User, title: String, address: String) {
        val address = Address(title, address)
        currentUser.address.add(address)
    }

    fun removeAddress(address: Address, addressBook: ArrayList<Address>) {
        addressBook.remove(address)

    }

    fun createItem(
        selectedItem: Pizza,
        quantity: Int,
        selectedToppings: ArrayList<Topping>,
        selectedSize: Size,
        totalPrice: Int
    ): Item {
        return Item(selectedItem, quantity, selectedToppings, selectedSize, totalPrice)
    }

    fun removeItemFromCart(cart: ArrayList<Item>, item: Item) {
        cart.remove(item)
    }

    private fun clearCart(userId: Int) {
        val user = Database.listOfUsers.filter { it.id == userId }[0]
        user.cart.clear()
    }
}