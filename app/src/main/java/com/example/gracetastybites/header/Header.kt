package com.example.gracetastybites.header

import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gracetastybites.R
import com.example.gracetastybites.ui.theme.SemiBoldLabelLarge

@Composable
fun Header(sharedPreferences: SharedPreferences, navManager: NavController) {
    val context = LocalContext.current
    val profilePicName = sharedPreferences.getString("profilePic", "") ?: ""
    val drawableId = remember(profilePicName) {
        context.resources.getIdentifier(profilePicName, "drawable", context.packageName)
    }

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
}