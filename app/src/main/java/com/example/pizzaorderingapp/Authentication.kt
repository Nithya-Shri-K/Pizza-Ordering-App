package com.example.pizzaorderingapp

class Authentication {
    fun login(phoneNumber: String, password: String): Users? {
        val account = Database.listOfUsers.filter { it.phoneNumber == phoneNumber }
        if (account[0].password == password)
            return account[0]
        return null
    }
}