package com.example.gracetastybites

import android.content.Context
import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gracetastybites.ui.theme.GraceTastyBitesTheme
import com.example.gracetastybites.logo.Logo
import com.example.gracetastybites.button.ReusableButton
import com.example.gracetastybites.socialMedia.SocialMediaLoop
import com.example.gracetastybites.sqllite.DatabaseHelper

//Screen Import
import com.example.gracetastybites.screens.LoginScreen
import com.example.gracetastybites.screens.SignUpScreen
import com.example.gracetastybites.screens.AdminDashboardScreen
import com.example.gracetastybites.screens.Terms
import com.example.gracetastybites.screens.admin.AdminStuffAndRole
import com.example.gracetastybites.screens.admin.AddEmployee
import com.example.gracetastybites.screens.admin.AdminViewUser
import com.example.gracetastybites.screens.admin.AssignShit
import com.example.gracetastybites.screens.admin.FilterDate
import com.example.gracetastybites.screens.admin.ShiftsPerEmployee
import com.example.gracetastybites.screens.admin.ViewShifts

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GraceTastyBitesTheme {

                val navManager = rememberNavController();
                val dbHelper = DatabaseHelper(this);

                //Cookies
                val sharedPreferences = getSharedPreferences("AuthPrefs", Context.MODE_PRIVATE)
                val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
                val savedEmail = sharedPreferences.getString("email", "") ?: "";
                val userRole = sharedPreferences.getString("userRole", "") ?: "";


                // NavHost goes here
                NavHost(
                    navController = navManager,
                    startDestination = "home"
                ) {
                    composable("home") {
                        HomeScreen(navManager, isLoggedIn, userRole, savedEmail)
                    }
                    composable("login") {
                        LoginScreen(navManager, dbHelper, sharedPreferences)
                    }
                    composable("admin-dashboard") {
                        AdminDashboardScreen(navManager, dbHelper, sharedPreferences)
                    }
                    composable("staff-dashboard") {
                        AdminDashboardScreen(navManager, dbHelper, sharedPreferences)
                    }
                    composable("forgot-password") {
                        Text("Forgot my password")
                    }
                    composable("sign-up") {
                        SignUpScreen(navManager, dbHelper)
                    }
                    composable("terms") {
                        Terms(navManager)
                    }
                    //Admin Routes
                    composable("staff-role") {
                        AdminStuffAndRole(navManager,dbHelper, sharedPreferences)
                    }
                    composable("view-user?id={id}") { backStackEntry ->
                        val id = backStackEntry.arguments?.getString("id")
                        if (id != null) {
                            AdminViewUser(navManager, dbHelper, sharedPreferences, id.toString())
                        }
                    }

                    composable("add-employee") {
                        AddEmployee(navManager,dbHelper, sharedPreferences)
                    }

                    composable("filter-date") {
                        FilterDate(navManager, dbHelper, sharedPreferences)
                    }

                    composable("shifts-employee") {
                        ShiftsPerEmployee(navManager, dbHelper, sharedPreferences)
                    }

                    composable("add-invoice") {
                        Text("add-invoice")
                    }
                    composable("manage-shifts") {
                        ViewShifts(navManager, dbHelper, sharedPreferences)
                    }
                    composable("assign-shift") {
                        AssignShit(navManager, dbHelper, sharedPreferences)
                    }
                    composable("manage-menu") {
                        Text("manage-menu")
                    }
                    composable("payroll") {
                        Text("payroll")
                    }
                }
            }
        }
    }

    @Composable
    fun HomeScreen(navManager: NavController, isLoggedIn: Boolean, userRole: String, savedEmail: String) {

        val db = DatabaseHelper(this).writableDatabase

        val bgCream = MaterialTheme.colorScheme.background;
        val welcomeTextParagraph = "Northampton’s home of crispy, golden, juicy, and packed with flavour.\u2028 Freshly made, always tasty."

        Column(
            modifier = Modifier.fillMaxSize().background(bgCream),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row() {
                Logo(440,440);
            }
            Row(
                modifier = Modifier.fillMaxWidth(0.8f).padding(top = 48.dp)
            ) {
                Text(text = "Welcome to Grace Tasty Bites",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center)
            }
            Row(
                modifier = Modifier.fillMaxWidth(0.8f).padding(top = 24.dp)
            ) {
                Text(text = welcomeTextParagraph,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center)
            }

            Row(
                modifier = Modifier.fillMaxWidth(0.8f).padding(top = 48.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                println(isLoggedIn)
                println("User Role is:")
                println(userRole)
                if (isLoggedIn && userRole.isNotBlank()) {
                    when (userRole) {
                        "admin" -> ReusableButton("Let's Eat!", "admin-dashboard", 150, 57, navManager = navManager)
                        "customer" -> ReusableButton("Let's Eat!", "customer-dashboard", 150, 57, navManager = navManager)
                        "staff" -> ReusableButton("Let's Eat!", "staff-dashboard", 150, 57, navManager = navManager)
                        else ->  ReusableButton("Let's Eat!", "login", 150, 57, navManager = navManager)

                    }
                } else {
                    ReusableButton("Let's Eat!", "login", 150, 57, navManager = navManager)
                }
//            ReusableButton("Let's Eat!", "login", 150, 57, navManager = navManager)
            }
            Row(
                modifier = Modifier.fillMaxWidth(0.8f).padding(top = 48.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                SocialMediaLoop()
            }

        }

    }}