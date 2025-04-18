package com.example.gracetastybites.screens

import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lint.kotlin.metadata.Visibility
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gracetastybites.R
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
fun SignUpScreen(navManager: NavController, dbHelper: DatabaseHelper) {

    val bgCream = MaterialTheme.colorScheme.background

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var firstname by rememberSaveable { mutableStateOf("") }
    var lastname by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var isChecked by remember { mutableStateOf(false) }

    val passwordNoMatch = password != confirmPassword

    val newUser = UserAuth(
        email = email,
        password = password,
        firstname = firstname,
        lastname = lastname,
        position = "Not Assigned",
        role = "staff",
        profilePic = "",
    )
    val context = LocalContext.current

    val db = DatabaseHelper(context)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgCream),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 23.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(0.8f)
            ) {
                    Button(
                        onClick = { navManager.navigate("login") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Unspecified
                        ),
                        elevation = null, // removes shadow
                        contentPadding = PaddingValues(0.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.back),
                                contentDescription = "Go Back",
                                modifier = Modifier
                                    .width(22.dp)
                                    .height(22.dp),
                                contentScale = ContentScale.Fit
                                )
                            }
            }
            Box(
                modifier = Modifier
                    .weight(1.2f),
                contentAlignment = Alignment.CenterStart,

            ) {
                Logo(75,75, Alignment.CenterStart)
            }
          }
        HorizontalDivider(thickness = 1.dp, color = Color(0x2F1B0C20))

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 23.dp, start = 27.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Welcome",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 23.dp, start = 27.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Please tell us about yourself.",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 23.dp, start = 27.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "We won't share any of your personal data.",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(top = 36.dp, start = 30.dp, end = 50.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            TextField(
                value = email,
                onValueChange = { email = it },
                placeholder = {
                    Text(
                        text = "Email Address",
                        style = TextStyle(fontWeight = FontWeight.Medium)
                    )},
                label = { Text(text ="Email Address1",
                    style = LabelInput,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                    textAlign = TextAlign.Start)},
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.16f),
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.16f),

                ),
                singleLine = true,
                textStyle = TextStyle(fontWeight = FontWeight.Medium),
                maxLines = 1
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(top = 12.dp, start = 30.dp, end = 50.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            TextField(
                value = firstname,
                onValueChange = { firstname = it },
                placeholder = {
                    Text(
                        text = "Firstname",
                        style = TextStyle(fontWeight = FontWeight.Medium)
                    )},
                label = { Text(text ="Firstname",
                    style = LabelInput,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                    textAlign = TextAlign.Start)},
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.16f),
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.16f),

                    ),
                singleLine = true,
                textStyle = TextStyle(fontWeight = FontWeight.Medium),
                maxLines = 1
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(top = 12.dp, start = 30.dp, end = 50.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            TextField(
                value = lastname,
                onValueChange = { lastname = it },
                placeholder = {
                    Text(
                        text = "Lastname",
                        style = TextStyle(fontWeight = FontWeight.Medium)
                    )},
                label = { Text(text ="Lastname",
                    style = LabelInput,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                    textAlign = TextAlign.Start)},
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.16f),
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.16f),

                    ),
                singleLine = true,
                textStyle = TextStyle(fontWeight = FontWeight.Medium),
                maxLines = 1
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(top = 12.dp, start = 30.dp, end = 50.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            TextField(
                value = password,
                onValueChange = { password = it },
                placeholder = {
                    Text(
                        text = "Password",
                        style = TextStyle(fontWeight = FontWeight.Medium)
                    )},
                label = { Text(text ="Password",
                    style = LabelInput,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                    textAlign = TextAlign.Start)},
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.16f),
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.16f),
                ),
                singleLine = true,
                textStyle = TextStyle(fontWeight = FontWeight.Medium),
                trailingIcon = {
                    if (passwordVisible) {
                        IconButton(onClick = { passwordVisible = false }) {
                            Icon(
                                imageVector = Icons.Filled.Visibility,
                                contentDescription = "hide_password"
                            )
                        }
                    } else {
                        IconButton(
                            onClick = { passwordVisible = true }) {
                            Icon(
                                imageVector = Icons.Filled.VisibilityOff,
                                contentDescription = "hide_password"
                            )
                        }
                    }
                },
                isError = passwordNoMatch,
                maxLines = 1,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(), //https://alitalhacoban.medium.com/show-hide-password-jetpack-compose-d0c4abac568f
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(top = 12.dp, start = 30.dp, end = 50.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = {
                    Text(
                        text = "Confirm Password",
                        style = TextStyle(fontWeight = FontWeight.Medium)
                    )},
                label = { Text(text ="Confirm Password",
                    style = LabelInput,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                    textAlign = TextAlign.Start)},
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.16f),
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.16f),
                ),
                singleLine = true,
                trailingIcon = {
                    if (passwordVisible) {
                        IconButton(onClick = { passwordVisible = false }) {
                            Icon(
                                imageVector = Icons.Filled.Visibility,
                                contentDescription = "hide_password"
                            )
                        }
                    } else {
                        IconButton(
                            onClick = { passwordVisible = true }) {
                            Icon(
                                imageVector = Icons.Filled.VisibilityOff,
                                contentDescription = "hide_password"
                            )
                        }
                    }
                },
                maxLines = 1,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(), //https://alitalhacoban.medium.com/show-hide-password-jetpack-compose-d0c4abac568f
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 20.dp)
        ) {

            Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it }
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "I agree to the Terms of Services & Privacy Policy",
                style = MediumRoboto16,
                modifier = Modifier.clickable {
                    navManager.navigate("terms")
                }
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 36.dp)
        ) {

            ReusableButton("Sign Up","login", 158, 60, navManager, otherCommands = {
                val rowId = db.insertUser(newUser)
                println(rowId)
                if (rowId > 0) {
                    Toast.makeText(context, "User added successfully!", Toast.LENGTH_SHORT).show()
                    navManager.navigate("login");
                } else {
                    Toast.makeText(context, "Failed to add user.", Toast.LENGTH_SHORT).show()
                }

            })
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(top = 36.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(text = "Already have an account?",
                style = MediumRoboto16,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(text = "Login",
                style = MediumRoboto16,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.clickable {
                    navManager.navigate("login")
                }.padding(start = 4.dp)
            )
        }
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
@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    // You might need to provide fake NavController & DB helper here
    val fakeNav = rememberNavController()
    val context = LocalContext.current
    val dbHelper = DatabaseHelper(context)

    SignUpScreen(navManager = fakeNav, dbHelper = dbHelper)
}