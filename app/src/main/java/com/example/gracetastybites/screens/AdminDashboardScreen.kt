package com.example.gracetastybites.screens

import android.content.SharedPreferences
import android.graphics.drawable.Drawable
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gracetastybites.R
import com.example.gracetastybites.button.ReusableButton
import com.example.gracetastybites.logo.Logo
import com.example.gracetastybites.mockData.QuickActionItem

// Import Mock up data base user login
import com.example.gracetastybites.mockData.UserAuth
//import com.example.gracetastybites.mockData.tableUserAuth
import com.example.gracetastybites.sqllite.DatabaseHelper
import com.example.gracetastybites.ui.theme.LabelInput
import com.example.gracetastybites.ui.theme.SemiBoldLabelLarge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(navManager: NavController, dbHelper: DatabaseHelper, sharedPreferences: SharedPreferences) {
    println(sharedPreferences.getString("userRole", ""))

//    if (!sharedPreferences.getBoolean("isLoggedIn", false) ||
//        sharedPreferences.getString("userRole", "") != "admin") {
//        navManager.navigate("login")
//    }
    val bgCream = MaterialTheme.colorScheme.background

    val context = LocalContext.current
    val profilePicName = sharedPreferences.getString("profilePic", "") ?: ""
    val drawableId = remember(profilePicName) {
        context.resources.getIdentifier(profilePicName, "drawable", context.packageName)

    }

    val menuItems = listOf(
        QuickActionItem("Staff & Role", "Management", Icons.Default.PersonAdd, onClick = {navManager.navigate("staff-role")}),
        QuickActionItem("Add Invoice", "Payroll", Icons.Default.Add, onClick = {navManager.navigate("add-invoice")}),
        QuickActionItem("Manage Shifts", "", Icons.Default.Event, onClick = {navManager.navigate("manage-shifts")}),
        QuickActionItem("Manage Menu", "", Icons.AutoMirrored.Filled.MenuBook, onClick = {navManager.navigate("manage-menu")}),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgCream),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 23.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(0.65f).padding(start = 21.dp)
            ) {
                if (drawableId != 0) {
                    Image(
                        painter = painterResource(id = drawableId),
                    contentDescription = "User",
                    modifier = Modifier
                        .width(44.dp)
                        .height(44.dp),
                    contentScale = ContentScale.Fit
                )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = "User",
                        modifier = Modifier
                            .width(44.dp)
                            .height(44.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }
            Box(
                modifier = Modifier
                    .weight(1f),
                contentAlignment = Alignment.CenterStart,

                ) {
                val email = sharedPreferences.getString("email", "") ?: ""
                val firstname = sharedPreferences.getString("firstname", "") ?: ""
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Welcome back,",
                        style = SemiBoldLabelLarge,
                        color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.6f)
                    )
                    Text(text = firstname,
                        modifier = Modifier.padding(top = 4.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSecondary)
                }
            }
            Box(
                modifier = Modifier
                    .weight(0.65f).padding(end = 21.dp),
                contentAlignment = Alignment.CenterEnd,

                ) {
                Button(onClick = {
                    val editor = sharedPreferences.edit()
                    editor.remove("email")
                    editor.remove("firstname")
                    editor.remove("userRole")
                    editor.remove("isLoggedIn")
                    editor.apply()
                    navManager.navigate("login") // try by adding a backstackentry value login/{clearShared} and then in login clear sharePref one more time
                }) {
                    Text(text = "Log out")
                }
            }
        }
        HorizontalDivider(thickness = 1.dp, color = Color(0x2F1B0C20))
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
                            listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.onTertiary)
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

    }
}
//Image(
//painter = painterResource(id = R.drawable.logo),
//contentDescription = "Grace Tasty Bites Logo",
//modifier = Modifier
//.width(sizeWidth.dp)
//.height(sizeHeight.dp),
//contentScale = ContentScale.Fit
//)

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
                        listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.onTertiary)
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