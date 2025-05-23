package com.example.gracetastybites.screens

import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gracetastybites.button.ReusableButton
import com.example.gracetastybites.logo.Logo

// Import Mock up data base user login
import com.example.gracetastybites.mockData.UserAuth
import com.example.gracetastybites.sqllite.DatabaseHelper
import com.example.gracetastybites.ui.theme.LabelInput
import com.example.gracetastybites.ui.theme.MediumRoboto16
import com.example.gracetastybites.ui.theme.SemiBoldLabelLarge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navManager: NavController, dbHelper: DatabaseHelper, sharedPreferences: SharedPreferences) {
    val tableUserAuth = remember { mutableStateListOf<UserAuth>() }

    println("test login ${sharedPreferences.getString("userRole", "")}")

    val bgCream = MaterialTheme.colorScheme.background

    var email by remember { mutableStateOf("") };
    var password by remember { mutableStateOf("") };

    var firstname by remember { mutableStateOf("") }

    var wrongEmail by remember { mutableStateOf(false) };
    var wrongPassword by remember { mutableStateOf(false) };

    fun checkLoginDetails(email: String, password: String, databaseUser: List<UserAuth>): UserAuth? {

        val matchedUser = databaseUser.find { it.email == email } //https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/find.html
//        println("Hello, this is the return value of matchedUser --- let's see how we can extract it's nmae$matchedUser")
        if (matchedUser != null) {
            firstname = matchedUser.firstname
//            println("Matched User firstname is $firstname")
        }
        if(wrongEmail) {
            wrongEmail = false
        }

        if(wrongPassword) {
            wrongPassword = false
        }

        println("Test Invalid User - Debug as wrongEmail value is not going through when an invalid email entered.");
        println(matchedUser);
        if( matchedUser != null ) {
            if(password == matchedUser.password) {

                val editor = sharedPreferences.edit()
                editor.putString("email", email)
                editor.putString("firstname", firstname)
                editor.putBoolean("isLoggedIn", true)
                editor.putString("userRole", matchedUser.role)
                editor.putString("profilePic", matchedUser.profilePic)
                editor.apply()

                return matchedUser;
            } else {
                wrongPassword = true;
                return null;
            }
        } else {
            wrongEmail = true;
            return null;
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgCream),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row() {
            Logo(200,200);
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(top = 36.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Hello",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(top = 24.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Login into your account to get some goodies",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(top = 36.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(text = "Email Address",
                style = LabelInput,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Start
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(top = 12.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text ="Email Address",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start)},
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
                maxLines = 1
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(top = 12.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text ="Password",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start)},
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
                maxLines = 1,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(top = 12.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(text = "Forgot Password",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.clickable { //https://developer.android.com/develop/ui/compose/modifiers
                    navManager.navigate("forgot-password")
                }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(top = 36.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            ReusableButton("Login", "", 150, 57, navManager = navManager, otherCommands = {
                tableUserAuth.clear()
                tableUserAuth.addAll(dbHelper.getAllUsers())
                val userDetails = checkLoginDetails(email, password, tableUserAuth);


                println(userDetails);

                if(userDetails != null) {
                    if(userDetails.role == "admin") {
                        println("Condition true");
                        navManager.navigate("admin-dashboard")
                    } else if( userDetails.role == "staff") {
                        navManager.navigate(("staff-dashboard"))
                    } else if (userDetails.role == "customer") {
                        navManager.navigate("customer-dashboard")
                    } else {
                        navManager.navigate("home")
                    }
                }
            })
        }

        if(wrongEmail) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(top = 36.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Wrong Username - Please try again",
                    style = LabelInput,
                    color = MaterialTheme.colorScheme.primary)
            }
        }
        if(wrongPassword) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(top = 36.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Wrong Password - Please try again",
                    style = LabelInput,
                    color = MaterialTheme.colorScheme.primary)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(top = 75.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(text = "Don't have an account?",
                style = MediumRoboto16,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(text = "Join Now!",
                style = MediumRoboto16,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.clickable {
                    navManager.navigate("sign-up")
                }
            )
        }

    }
}