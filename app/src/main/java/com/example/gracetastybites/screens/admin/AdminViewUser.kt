package com.example.gracetastybites.screens.admin

import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.gracetastybites.header.Header
import com.example.gracetastybites.mockData.InsertItem
import com.example.gracetastybites.mockData.UserAuth
import com.example.gracetastybites.mockData.adminNavBarItems
import com.example.gracetastybites.navigationBar.NavigationBar
import com.example.gracetastybites.sqllite.DatabaseHelper
import com.example.gracetastybites.ui.theme.MediumRoboto16
import com.example.gracetastybites.ui.theme.SemiBoldLabelLarge

@Composable
fun AdminViewUser(navManager: NavController, dbHelper: DatabaseHelper, sharedPreferences: SharedPreferences, id: String) {
    val getId = id.toInt()
    println("View User Id is $getId")
    val getUserDetails = dbHelper.getUserById(getId)
    val firstName = remember { mutableStateOf(getUserDetails?.firstname  ?: " ")  }
    val lastName = remember { mutableStateOf(getUserDetails?.lastname ?: " " ) }
    val emailEmployee = remember { mutableStateOf(getUserDetails?.email ?: " " ) }
    val position = remember { mutableStateOf(getUserDetails?.position ?: " ")  }
    val role = remember { mutableStateOf(getUserDetails?.role ?: " ")}
//    val defaultPassword = remember { mutableStateOf(getUserDetails?.password  ?: " ")}

    val insertItems = listOf(
        InsertItem(Icons.Default.Person, "First Name", firstName, "Insert First Name"),
        InsertItem(Icons.Default.Person, "Last Name", lastName, "Insert Last Name"),
        InsertItem(Icons.Default.Email, "Email", emailEmployee, "Insert Email"),
        InsertItem(Icons.Default.Work, "Position", position, "Insert New Position"),
        InsertItem(Icons.Default.WorkspacePremium, "Role", role, "Insert New Role"),
//        InsertItem(Icons.Default.Password, "Password", defaultPassword, "Insert New Password"),
//        InsertItem(Icons.Default.Password, "Renter Password", defaultPassword, "Renter Password")
    )

    val userId = getUserDetails?.id
    val fetPassword = getUserDetails?.password ?: "plm123"

    val updatedEmployee = UserAuth(
        id = userId ?: 0,
        firstname = firstName.value,
        lastname = lastName.value,
        email = emailEmployee.value,
        position = position.value,
        role = role.value,
        profilePic = getUserDetails?.profilePic ?: " ",
        password = fetPassword
    )

    val bgCream = MaterialTheme.colorScheme.background

    val context = LocalContext.current

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

            insertItems.forEach { item ->
                InsertItemCard(
                    icon = item.icon,
                    label = item.label,
                    value = item.value.value,
                    placeholder = item.placeholder,
                    onValueChange = { item.value.value = it }
                )
            }
            ReusableButton("Update Employee Details", "", 350, 50, navManager = navManager, otherCommands = {
                val userRowId = dbHelper.updateUser(updatedEmployee)
                if (userRowId > 0) {
                    Toast.makeText(context, "Employee added successfully!", Toast.LENGTH_SHORT).show()
                    navManager.navigate("staff-role");
                } else {
                    Toast.makeText(context, "Failed to add user.", Toast.LENGTH_SHORT).show()
                }
            })
            ReusableButton("Back", "", 150, 50, navManager = navManager, otherCommands = {
                navManager.popBackStack()
            })
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
