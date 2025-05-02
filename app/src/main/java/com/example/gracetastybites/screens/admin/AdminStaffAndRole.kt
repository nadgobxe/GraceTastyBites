package com.example.gracetastybites.screens.admin

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
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
import com.example.gracetastybites.button.ReusableButton
import com.example.gracetastybites.mockData.NavBarItem
import com.example.gracetastybites.mockData.UserAuth
import com.example.gracetastybites.mockData.adminNavBarItems
import com.example.gracetastybites.navigationBar.NavigationBar
import com.example.gracetastybites.screens.MenuItemCard
import com.example.gracetastybites.screens.QuickActionItemCard
import com.example.gracetastybites.sqllite.DatabaseHelper
import com.example.gracetastybites.ui.theme.SemiBoldLabelLarge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminStuffAndRole(navManager: NavController, dbHelper: DatabaseHelper, sharedPreferences: SharedPreferences) {



    val bgCream = MaterialTheme.colorScheme.background

    val context = LocalContext.current
    val profilePicName = sharedPreferences.getString("profilePic", "") ?: ""
    val drawableId = remember(profilePicName) {
        context.resources.getIdentifier(profilePicName, "drawable", context.packageName)
    }



    val allUsers = dbHelper.getAllUsers().sortedBy { it.firstname }

    println(allUsers)

    val drawableIdTest = context.resources.getIdentifier("admin".toString(), "drawable", context.packageName)
    println("I test the image id $drawableIdTest")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
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
                    .weight(0.65f)
                    .padding(start = 21.dp)
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
                    .weight(0.65f)
                    .padding(end = 21.dp),
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
            .padding(top = 50.dp, start = 20.dp))
        {
            Text(text = "Staff & Role Management",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 20.dp))
        }
        ReusableButton("Add Employee", "add-employee", 374, 50, navManager)
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp, start = 20.dp))
        {
            Text(text = "View Staff Members",
                style = MaterialTheme.typography.bodyLarge)
        }
        LazyColumn() {
            items(items = allUsers, itemContent = {item ->  ListUser(item, navManager, context, dbHelper) })
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

// Reusable Composable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListUser(item:UserAuth, navManager: NavController, context: Context, dbHelper: DatabaseHelper) {

    val showDialog = remember { mutableStateOf(false) }

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 20.dp, start = 20.dp, end = 20.dp)
        .clickable { navManager.navigate("view-user") },
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center) // remember about backstack entry
    {
        Row(modifier = Modifier
            .weight(0.2f)
            .height(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            val position = "waiter"

            val pictureName = when (item.position) {
                "waiter" -> "waiter"
                "chief" -> "chief"
                "cashier" -> "cashier"
                "manager" -> "manager"
                else -> "not available"
            }
            val drawableId = context.resources.getIdentifier(pictureName, "drawable", context.packageName)

            if (drawableId != 0) {
                Image(
                    painter = painterResource(id = drawableId),
                    contentDescription = "Position Logo",
                    modifier = Modifier
                        .height(20.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.admin),
                    contentDescription = "Default image",
                    modifier = Modifier
                        .height(20.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
        Row(modifier = Modifier.weight(0.8f),  verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start) {
            Text(text=item.firstname + " " + item.lastname, style = MaterialTheme.typography.bodyLarge)
        }
        Row(modifier = Modifier.weight(0.2f),  verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.height(40.dp).clickable {
                        showDialog.value = true
                    }

            )

            if (showDialog.value) {
                AlertDialog(
                    onDismissRequest = { showDialog.value = false },
                    title = { Text("Confirm Delete") },
                    text = { Text("Are you sure that you want to delete user ${item.firstname} ${item.lastname}?") },
                    confirmButton = {
                        Text(
                            text = "Delete",
                            modifier = Modifier.clickable {
                                Toast.makeText(context, "Employee deleted successfully!", Toast.LENGTH_SHORT).show()
                                dbHelper.deleteUserById(item.id)
                                navManager.navigate("staff-role");
                                showDialog.value = false
                            },
                            color = Color.Red
                        )
                    },
                    dismissButton = {
                        Text(
                            text = "Cancel",
                            modifier = Modifier.clickable {
                                showDialog.value = false
                            }
                        )
                    }
                )
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondary,
//                    modifier = Modifier.height(40.dp)
            )
        }

    }
    HorizontalDivider(thickness = 1.dp, color = Color(0x2F1B0C20), modifier = Modifier.padding(top = 10.dp , start = 20.dp, end = 20.dp))
}
