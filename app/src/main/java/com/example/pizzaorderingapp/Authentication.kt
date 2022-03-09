package com.example.pizzaorderingapp

class Authentication {
    fun login(phoneNumber: String, password: String): User? {
        val account = Database.listOfUsers.filter { it.phoneNumber == phoneNumber }
        if (account.isNotEmpty() && account[0].password == password) {
            return account[0]
        }
        return null
    }
}