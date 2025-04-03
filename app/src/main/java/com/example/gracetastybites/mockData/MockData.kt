package com.example.gracetastybites.mockData

data class UserAuth(
    val email: String,
    val password: String,
    val firstname: String,
    val lastname: String,
    val position: String,
    val role: String,
    val profilePic: String
)

val tableUserAuth = mutableListOf(
    UserAuth(email = "one", password = "plm123", firstname= "Bogdan", lastname = "Burcea", position = "Cleaning", role= "admin", profilePic = "")
)

data class MenuList(
    val name: String,
    val price: String,
    val category: String,
    val picture: String
)
