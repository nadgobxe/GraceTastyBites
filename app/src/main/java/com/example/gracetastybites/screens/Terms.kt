package com.example.gracetastybites.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gracetastybites.ui.theme.Roboto24
import com.example.gracetastybites.ui.theme.SemiBoldLabelLarge
import com.example.gracetastybites.button.ReusableButton

@Composable
fun Terms(navManager: NavController) {
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight().background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp, top = 60.dp)
                    .width(170.dp)
                    .height(4.dp)
                    .clip(shape = RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)),
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                Row(modifier = Modifier.weight(0.5f).width(85.dp).height(4.dp).clip(shape = RoundedCornerShape(20.dp)).background(MaterialTheme.colorScheme.onTertiary)) {}
                Row(modifier = Modifier.weight(0.5f).width(85.dp).height(4.dp).clip(shape = RoundedCornerShape(20.dp))) {}
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
        )
        {
            Text(text = "Terms and Conditions",
                style = Roboto24,
                modifier = Modifier.padding(top = 20.dp).fillMaxWidth()
            )

        }
        Row() {
            Text(text = "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur.",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(20.dp).fillMaxWidth()
            )
        }
        Row() {
            Text(text = "From a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet.",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(20.dp).fillMaxWidth()
            )
        }
        Row() {
            Text(text = "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or avoids pleasure itself...",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(20.dp).fillMaxWidth()
            )
        }

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ReusableButton("Accept", "", 158, 60, navManager, otherCommands = { navManager.popBackStack() })
            ReusableButton("Decline", "home", 158, 60, navManager)
        }

    }
}