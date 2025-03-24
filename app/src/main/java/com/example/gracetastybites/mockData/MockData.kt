package com.example.gracetastybites.mockData

data class UserAuth(
    val username: String,
    val password: String,
    val role: String,
    val profilePic: String
)

val tableUserAuth = mutableListOf(
    UserAuth(username = "one", password = "plm123", role= "admin", profilePic = "")
)

data class MenuList(
    val name: String,
    val price: String,
    val category: String,
    val picture: String
)
