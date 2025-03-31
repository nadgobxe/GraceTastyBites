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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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

// Import Mock up data base user login
import com.example.gracetastybites.mockData.UserAuth
import com.example.gracetastybites.mockData.tableUserAuth
import com.example.gracetastybites.sqllite.DatabaseHelper
import com.example.gracetastybites.ui.theme.LabelInput
import com.example.gracetastybites.ui.theme.SemiBoldLabelLarge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(navManager: NavController, dbHelper: DatabaseHelper, sharedPreferences: SharedPreferences) {

    val bgCream = MaterialTheme.colorScheme.background


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
                    .weight(1f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = "User",
                    modifier = Modifier
                        .width(44.dp)
                        .height(44.dp),
                    contentScale = ContentScale.Fit
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f),
                contentAlignment = Alignment.CenterStart,

                ) {
                val email = sharedPreferences.getString("email", "") ?: ""
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Welcome back,")
                    Text(text = email)
                }
            }
            Box(
                modifier = Modifier
                    .weight(1f),
                contentAlignment = Alignment.CenterStart,

                ) {
                Button(onClick = {
                    val editor = sharedPreferences.edit()
                    editor.apply({
                        editor.remove("email")
                        editor.remove("userRole")
                        editor.putBoolean("isLoggedIn", false)
                        apply()
                    })
                    navManager.navigate("home")
                }) {
                    Text(text = "Log out")
                }
            }
        }
        HorizontalDivider(thickness = 1.dp, color = Color(0x2F1B0C20))

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