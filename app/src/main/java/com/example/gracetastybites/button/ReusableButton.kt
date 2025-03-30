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
/**
======================
 *@Reusable Button
Params:
 * @param buttonText -> String | Controls the button text
 * @param path -> String  | Controls the path where to redirect when pressing on the button | navManager.navigate(path)
 * path can be optional - if path is empty it will not redirect
 * @param sizeWidth -> Int | Controls the width of the button
 * @param sizeHeight -> Int  | Controls the height of the button
 * @param navManager -> NavController | We are passing the navManager from @composable to button's @composable. Used to handle navigation
 * @param otherCommands -> Function | We are passing anonymous or defined functions to handle the click or other actions
Reusable Button
======================
 */

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

