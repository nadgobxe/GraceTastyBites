package com.example.gracetastybites.button

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gracetastybites.ui.theme.ButtonTextStyle

//I created a reusable button to avoid coding it each time. It has multiple props:
// buttonText = button Text Label
// path = navigation path // added an if statement to check if path is not empty because of the login scenario. In case a empty value is passed to path then  navManager.navigate(path) won't go through and a value must be passed to otherCommands prop
// sizeWidth  and sizeHeight = Integer to set the width of the button
// navManager = passing the navManager prop
// otherCommands = Optional prop to pass functions of onClick button


@Composable
fun ReusableButton(buttonText: String, path: String, sizeWidth:Int, sizeHeight:Int, navManager: NavController, otherCommands: () -> Unit = {}) {

    Button(onClick = {
        otherCommands()
        if (path.isNotEmpty()) {
            navManager.navigate(path) //https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/is-not-empty.html
        }
                     },
        modifier = Modifier
        .width(sizeWidth.dp)
        .height(sizeHeight.dp)) {
        Text(text = buttonText,
            style = ButtonTextStyle
            )
    }
}

