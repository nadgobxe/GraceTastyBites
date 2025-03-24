package com.example.gracetastybites

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gracetastybites.ui.theme.GraceTastyBitesTheme
import com.example.gracetastybites.logo.Logo
import com.example.gracetastybites.button.ReusableButton
import com.example.gracetastybites.socialMedia.SocialMediaLoop

//Screen Import
import com.example.gracetastybites.screens.LoginScreen
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GraceTastyBitesTheme {

                val navManager = rememberNavController()

                // NavHost goes here
                NavHost(
                    navController = navManager,
                    startDestination = "home"
                ) {
                    composable("home") {
                        HomeScreen(navManager)
                    }
                    composable("login") {
                         LoginScreen(navManager)
                    }
                    composable("admin-dashboard") {
                        Text("admin")
                    }
                    composable("forgot-password") {
                        Text("Forgot my password")
                    }
                    composable("sign-up") {
                        Text("Sign Up")
                    }
                }
        }
    }
}

@Composable
fun HomeScreen(navManager: NavController) {
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
            ReusableButton("Let's Eat!", "login", 150, 57, navManager = navManager)
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