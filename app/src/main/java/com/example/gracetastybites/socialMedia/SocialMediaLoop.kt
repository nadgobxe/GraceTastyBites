package com.example.gracetastybites.socialMedia

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.gracetastybites.R

@Composable
fun SocialMediaLoop() {

    data class SocialMediaItem(
        val name: String,
        val url: String,
        val iconPath: String
    )

    val socialMediaItems = mutableListOf(
        SocialMediaItem(name = "Facebook", url = "facebook.com", iconPath = "ic_facebook"),
        SocialMediaItem(name = "Instagram", url = "facebook1.com", iconPath = "ic_insta_fill"),
        SocialMediaItem(name ="Pinterest", url = "pinterest.com" , iconPath = "ic_tiktok_solid"),
        SocialMediaItem(name ="TikTok", url = "tiktok.com" , iconPath = "ic_pinterest")
    )

    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        socialMediaItems.forEach{ item ->
            val context = LocalContext.current
            val imageId = remember {
                context.resources.getIdentifier(item.iconPath, "drawable", context.packageName)
            } //https://stackoverflow.com/questions/70062705/how-can-i-get-drawable-resource-by-string

                Column() {
                    val imagePath = item.iconPath;
                    Image(
                        painter =  painterResource(imageId),
                        contentDescription = item.name,
                        modifier = Modifier.size(48.dp)
                    )
                }
        }
    }
}