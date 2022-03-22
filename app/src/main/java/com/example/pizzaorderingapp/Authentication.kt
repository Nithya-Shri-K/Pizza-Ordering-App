package com.example.pizzaorderingapp

class Authentication {
    fun login(phoneNumber: String, password: String, databaseHelper: DatabaseHelper): Int {
        val userId = databaseHelper.isAccountExist(phoneNumber)
        return if (userId != -1) {
            val accountPassword = databaseHelper.getPassword(userId)
            if (password == accountPassword)
                userId
            else
                INVALID_PASSWORD
        } else
            INVALID_ACCOUNT
    }
}