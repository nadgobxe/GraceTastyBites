package com.example.gracetastybites.logo

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.*
import com.example.gracetastybites.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*

/**
======================
*@Reusable Logo
Params:
*@param sizeWidth -> Int | Controls the width of the image
*@param sizeHeight -> Int  | Controls the height of the image
*@param contentAlignment -> String | Default is Alignment.Center
 Reusable Logo
======================
*/

@Composable
fun Logo(sizeWidth: Int, sizeHeight: Int, contentAlignment: Alignment = Alignment.Center) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
            contentAlignment = contentAlignment
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Grace Tasty Bites Logo",
            modifier = Modifier
                .width(sizeWidth.dp)
                .height(sizeHeight.dp),
            contentScale = ContentScale.Fit
        )
    }
}
