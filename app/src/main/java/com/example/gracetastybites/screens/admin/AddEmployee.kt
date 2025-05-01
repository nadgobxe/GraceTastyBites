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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gracetastybites.R
import com.example.gracetastybites.button.ReusableButton
import com.example.gracetastybites.mockData.NavBarItem
import com.example.gracetastybites.mockData.UserAuth
import com.example.gracetastybites.navigationBar.NavigationBar
import com.example.gracetastybites.screens.MenuItemCard
import com.example.gracetastybites.screens.QuickActionItemCard
import com.example.gracetastybites.sqllite.DatabaseHelper
import com.example.gracetastybites.ui.theme.MediumRoboto16
import com.example.gracetastybites.ui.theme.SemiBoldLabelLarge


@Composable
fun AddEmployee(navManager: NavController, dbHelper: DatabaseHelper, sharedPreferences: SharedPreferences) {

    val adminNavBarItems = listOf(
        NavBarItem("Home", Icons.Default.Home, onClick = {
            navManager.navigate("admin-dashboard")
            println("test if my logic works")}),
        NavBarItem("Employees", Icons.Default.PersonSearch, onClick = {navManager.navigate("admin-dashboard")}),
        NavBarItem("Invoice", Icons.Default.Newspaper, onClick = {navManager.navigate("admin-dashboard")}),
        NavBarItem("Shifts", Icons.Default.CalendarMonth, onClick = {navManager.navigate("admin-dashboard")}),
        NavBarItem("Menus", Icons.Default.MenuBook, onClick = {navManager.navigate("admin-dashboard")}),
        NavBarItem("Payroll", Icons.Default.Paid, onClick = {navManager.navigate("admin-dashboard")}),
    )

    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val emailEmployee = remember { mutableStateOf("") }
    val position = remember { mutableStateOf("") }
    val role = remember { mutableStateOf("staff") }
    val defaultPassword = remember { mutableStateOf("plm123") }

    val newEmployee = UserAuth(
        id = 0,
        firstname = firstName.value,
        lastname = lastName.value,
        email = emailEmployee.value,
        position = position.value,
        password = defaultPassword.value,
        role = role.value,
        profilePic = ""
    )

    val bgCream = MaterialTheme.colorScheme.background

    val context = LocalContext.current
    val profilePicName = sharedPreferences.getString("profilePic", "") ?: ""
    val drawableId = remember(profilePicName) {
        context.resources.getIdentifier(profilePicName, "drawable", context.packageName)
    }

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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InsertItemCard(Icons.Default.Person, label = "First Name", firstName.value, placeholder = "Insert First Name", onValueChange = {firstName.value = it})
            InsertItemCard(Icons.Default.Person, label = "Last Name", lastName.value, placeholder = "Insert Last Name", onValueChange = {lastName.value = it})
            InsertItemCard(Icons.Default.Email, label = "Email", emailEmployee.value, placeholder = "Insert Email", onValueChange = {emailEmployee.value = it})
            InsertItemCard(Icons.Default.Work, label = "Position", position.value, placeholder = "Insert New Position", onValueChange = {position.value = it})
            InsertItemCard(Icons.Default.WorkspacePremium, label = "Role", role.value, placeholder = "Insert New Role", onValueChange = {role.value = it})
            ReusableButton("Add Employee", "", 250, 50, navManager = navManager, otherCommands = {
               val rowId = dbHelper.insertUser(newEmployee)
                if (rowId > 0) {
                    Toast.makeText(context, "Employee added successfully!", Toast.LENGTH_SHORT).show()
                    navManager.navigate("staff-role");
                } else {
                    Toast.makeText(context, "Failed to add user.", Toast.LENGTH_SHORT).show()
                }
            })
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavigationBar(navManager, adminNavBarItems)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertItemCard(
    icon: ImageVector,
    label: String,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(44.dp)
                .height(44.dp)
                .clip(RoundedCornerShape(50))
                .background(Color(0xFFD84444)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = Color(0xFF7A6651)
            )
            TextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = placeholder,
                    style = MediumRoboto16,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start)},
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    focusedIndicatorColor = Color(0x2F1B0C20),
                    unfocusedPlaceholderColor = Color(0x2F1B0C20),
                ),
            )
        }
    }
}
