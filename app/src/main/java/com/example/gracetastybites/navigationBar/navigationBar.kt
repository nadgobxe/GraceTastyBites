package com.example.gracetastybites.navigationBar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gracetastybites.mockData.NavBarItem
import com.example.gracetastybites.sqllite.DatabaseHelper
import com.example.gracetastybites.ui.theme.SmallRoboto10

@Composable
fun NavigationBar(navManager: NavController, navigationItems: List<NavBarItem>, dbHelper: DatabaseHelper) {
    val extractCurrentIndex = dbHelper.getMenuColValue()
    println(extractCurrentIndex)
    var selectedIndex by rememberSaveable { mutableIntStateOf(extractCurrentIndex) }

    Column(
        modifier = Modifier.fillMaxWidth().height(94.dp).background(MaterialTheme.colorScheme.onTertiary)
    ) {
        Row(
            modifier = Modifier.weight(1f).height(50.dp).fillMaxWidth()
        ) {
            navigationItems.forEachIndexed{index, item ->
            NavigationItemShow(navManager, item, index, selectedIndex, item.path, onItemSelected = { newIndex -> selectedIndex =
                newIndex as Int
            }, dbHelper)
        }

        }

        Row(
            modifier = Modifier.weight(1f).background(MaterialTheme.colorScheme.onSurface).height(44.dp).fillMaxWidth()
        ) {}
    }
}


@Composable
fun NavigationItemShow(navManager: NavController, item: NavBarItem, index: Int, selectedIndex: Int, path: String, onItemSelected: (Any?) -> Unit, dbHelper: DatabaseHelper) {
//    val currentPath = navManager.currentBackStackEntry?.destination?.route
//    println(currentPath) || path == currentPath
//    dbHelper.updateColValue(0)  // reset
//    if (path != currentPath) {
//        dbHelper.updateColValue(0)  // reset
//    }
    val bgColor = if (index == selectedIndex ) {
        MaterialTheme.colorScheme.onSurface
    } else {
        MaterialTheme.colorScheme.onTertiary
    }

    Column(
        modifier = Modifier.width(72.dp).height(50.dp).background(bgColor).clickable {
            onItemSelected(index)
            dbHelper.updateColValue(index)
            navManager.navigate(path)
        },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(MaterialTheme.colorScheme.onTertiary)
        ) {}
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
            ) {


                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(top = 2.dp)
                )

                Text(
                    text = item.title,
                    color = MaterialTheme.colorScheme.background,
                    style = SmallRoboto10,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    textAlign = TextAlign.Center
                    )

        }
    }
}

