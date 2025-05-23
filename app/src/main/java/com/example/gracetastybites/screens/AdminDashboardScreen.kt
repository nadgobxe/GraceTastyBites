package com.example.gracetastybites.screens

import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gracetastybites.R
import com.example.gracetastybites.header.Header
import com.example.gracetastybites.mockData.MenuList
import com.example.gracetastybites.mockData.QuickActionItem

// Import Mock up data base user login
//import com.example.gracetastybites.mockData.tableUserAuth
import com.example.gracetastybites.sqllite.DatabaseHelper
import com.example.gracetastybites.ui.theme.SemiBoldLabelLarge
import com.example.gracetastybites.navigationBar.NavigationBar
import com.example.gracetastybites.mockData.NavBarItem
import com.example.gracetastybites.mockData.adminNavBarItems
import com.example.gracetastybites.ui.theme.LabelInput

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(navManager: NavController, dbHelper: DatabaseHelper, sharedPreferences: SharedPreferences) {
    println(sharedPreferences.getString("userRole", ""))

//    if (!sharedPreferences.getBoolean("isLoggedIn", false) ||
//        sharedPreferences.getString("userRole", "") != "admin") {
//        navManager.navigate("login")
//    }
    val bgCream = MaterialTheme.colorScheme.background




// debug - extract image id for pre-design
//    val drawableIdTest = context.resources.getIdentifier("chicken_nuggets".toString(), "drawable", context.packageName)
//    println("I test the image id $drawableIdTest")


    val menuItems = listOf(
        QuickActionItem("Staff & Role", "Management", Icons.Default.PersonAdd, onClick = {navManager.navigate("staff-role")}),
        QuickActionItem("Add Invoice", "Payroll", Icons.Default.Add, onClick = {navManager.navigate("add-invoice")}),
        QuickActionItem("Manage Shifts", "", Icons.Default.Event, onClick = {navManager.navigate("manage-shifts")}),
        QuickActionItem("Manage Menu", "", Icons.AutoMirrored.Filled.MenuBook, onClick = {navManager.navigate("manage-menu")}),
    )

      val menuFoodList = remember { dbHelper.getAllMenuItems() }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgCream),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header(sharedPreferences, navManager)
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 51.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.padding(start = 21.dp)
            ) {
                Text(text = "Quick Actions")
            }
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 21.dp, top = 15.dp),
            verticalAlignment = Alignment.CenterVertically) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(menuItems) { item ->
                    QuickActionItemCard(item) // points to line 212 where we are using a composable (component) to iterate through menuItem list
                }
            }

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 42.dp)
            ,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(191.dp)
                    .height(73.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.onTertiary
                            )
                        )
                    )
                    .padding(16.dp)
                    .clickable { navManager.navigate("payroll") },
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Payroll",
                    color = MaterialTheme.colorScheme.background,
                    style = MaterialTheme.typography.titleMedium)
            } }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, start = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Menu",
                    style = MaterialTheme.typography.bodyLarge)
                Row() {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(menuFoodList) { item ->
                            MenuItemCard(item)
                        }
                    }

                }

            }

        }


        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavigationBar(navManager, adminNavBarItems, dbHelper)
        }
    }
}


@Composable
fun QuickActionItemCard(item: QuickActionItem) {
    Column(
        modifier = Modifier.padding(end = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1.8f) // to keep it landscape-style
                .clip(RoundedCornerShape(12.dp))
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.onTertiary
                        )
                    )
                )
                .padding(16.dp)
                .clickable { item.onClick() },
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.background,
                )
            }
            Row( modifier = Modifier.padding(top = 24.dp, start = 12.dp))
            {
                Text(text = item.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.background)
            }
        }
    }
}

@Composable
fun MenuItemCard (item: MenuList) {
    Box(modifier = Modifier.padding(5.dp)) {
        Column(
            modifier = Modifier
                .width(200.dp)
                .height(240.dp)
                .clip(shape = RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.secondary)
        ) {
            Image(
                painter = painterResource(id = item.picture),
                contentDescription = "Item Food",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.padding(top = 10.dp, start = 15.dp, end = 15.dp, bottom = 10.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                Text(text = item.name, style = SemiBoldLabelLarge)
                Text(text = item.price, style = SemiBoldLabelLarge)
            }

            Row(
                modifier = Modifier.padding(start = 15.dp, end = 15.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                Text(text = "Category", style = LabelInput, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                Text(text = item.category, style = LabelInput, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
            }

            Row(
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp, top = 10.dp)
                    .fillMaxWidth()
                    .width(170.dp)
                    .height(4.dp)
                    .clip(shape = RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)),
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                Row(modifier = Modifier.weight(0.5f).width(85.dp).height(4.dp).clip(shape = RoundedCornerShape(20.dp)).background(MaterialTheme.colorScheme.onTertiary)) {}
                Row(modifier = Modifier.weight(0.5f).width(85.dp).height(4.dp).clip(shape = RoundedCornerShape(20.dp))) {}
            }

        }
    }
}