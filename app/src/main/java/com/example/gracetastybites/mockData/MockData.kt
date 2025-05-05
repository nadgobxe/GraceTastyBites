package com.example.gracetastybites.mockData

import android.graphics.drawable.Drawable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.vector.ImageVector

data class UserAuth(
    val id: Int,
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
    val picture: Int
)

data class NavBarItem(
    val title: String,
    val icon: ImageVector,
    val path: String,
)


data class InsertItem(
    val icon: ImageVector,
    val label: String,
    var value: MutableState<String>,
    val placeholder: String
)

val adminNavBarItems = listOf(
    NavBarItem("Home", Icons.Default.Home, "admin-dashboard"),
    NavBarItem("Employees", Icons.Default.PersonSearch, "staff-role"),
    NavBarItem("Invoice", Icons.Default.Newspaper, "login"),
    NavBarItem("Shifts", Icons.Default.CalendarMonth, "manage-shifts"),
    NavBarItem("Menus", Icons.Default.MenuBook, ""),
    NavBarItem("Payroll", Icons.Default.Paid, ""),
)

data class Shift(
    val id: Int = 0,
    val staffId: Int,
    val shiftDate: String,
    val startTime: String,
    val endTime: String,
    val status: String = "pending"
)


