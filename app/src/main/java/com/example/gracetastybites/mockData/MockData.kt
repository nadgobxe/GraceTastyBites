package com.example.gracetastybites.mockData

import android.graphics.drawable.Drawable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.ui.graphics.vector.ImageVector

data class UserAuth(
    val email: String,
    val password: String,
    val firstname: String,
    val lastname: String,
    val position: String,
    val role: String,
    val profilePic: String
)

data class QuickActionItem(
    val title: String,
    val subtitle: String? = null,
    val icon: ImageVector,
    val onClick: () -> Unit
)

//val tableUserAuth = mutableListOf(
//    UserAuth(email = "one", password = "123", firstname= "Bogdan", lastname = "Burcea", position = "Cleaning", role= "admin", profilePic = "")
//)

data class MenuList(
    val name: String,
    val price: String,
    val category: String,
    val picture: String
)

data class NavBarItem(
    val title: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)


