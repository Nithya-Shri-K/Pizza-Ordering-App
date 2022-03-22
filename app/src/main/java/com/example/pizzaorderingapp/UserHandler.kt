package com.example.pizzaorderingapp

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object UserHandler {

    fun createUser(
        firstname: String,
        lastname: String,
        phoneNumber: String,
        password: String,
        databaseHelper: DatabaseHelper
    ): Int {
        val userId = databaseHelper.insertUsers("$firstname $lastname", phoneNumber, password, 0)
        return userId
    }

    fun addToCart(userId: Int,itemId: Int,databaseHelper: DatabaseHelper) {
       databaseHelper.insertCartData(userId,itemId)
    }

    fun placeOrder(
        userId: Int,
        totalPrice: Int,
        status: Status,
        deliveryAddress: Int,
        databaseHelper: DatabaseHelper
    ): Int {
        val currentDate = Calendar.getInstance().time
        val myFormat = "dd-MM-yyyy"
        val formattedDate = SimpleDateFormat(myFormat, Locale.UK)
        val selectedDate = formattedDate.format(currentDate)
        val orderId = databaseHelper.insertOrderData(
            userId,
            totalPrice,
            status.name,
            selectedDate,
            deliveryAddress
        )
        clearCart(userId, databaseHelper)
        return orderId
    }

    fun addAddress(
        currentUserId: Int,
        addressTag: String,
        address: String,
        databaseHelper: DatabaseHelper
    ) {
        val addressId = databaseHelper.insertAddress(address, addressTag)
        databaseHelper.insertUserAddress(currentUserId, addressId)
    }

    fun removeAddress(addressId: Int, databaseHelper: DatabaseHelper) {
        databaseHelper.deleteAddress(addressId)
    }


    fun createItem(
        pizzaId: Int,
        quantity: Int,
        selectedSize: Size,
        totalPrice: Int,
        toppingsSelected: ArrayList<Int>,
        databaseHelper: DatabaseHelper
    ): Int {
        val itemId = databaseHelper.insertItemData(
            pizzaId,
            quantity,
            selectedSize,
            totalPrice,
            toppingsSelected
        )
        return itemId
    }

    fun removeItemFromCart(itemId: Int, databaseHelper: DatabaseHelper) {
        databaseHelper.deleteItemFromCart(itemId)
    }

    fun clearCart(userId: Int, databaseHelper: DatabaseHelper) {
        databaseHelper.deleteCart(userId)

    }
}