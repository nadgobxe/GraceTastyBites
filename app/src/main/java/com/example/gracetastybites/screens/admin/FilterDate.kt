package com.example.gracetastybites.screens.admin

import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gracetastybites.header.Header
import com.example.gracetastybites.mockData.Shift
import com.example.gracetastybites.mockData.adminNavBarItems
import com.example.gracetastybites.navigationBar.NavigationBar
import com.example.gracetastybites.sqllite.DatabaseHelper

@Composable
fun FilterDate(navManager: NavController, dbHelper: DatabaseHelper, sharedPreferences: SharedPreferences) {
    val bgCream = MaterialTheme.colorScheme.background
    var shiftDate by remember { mutableStateOf<Long?>(null) }
    var shifts by remember { mutableStateOf<List<Shift>>(emptyList()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgCream),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header(sharedPreferences, navManager)
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            DatePickerFieldToModal(
                shiftDate = shiftDate,
                onDateChange = {
                    shiftDate = it
//                    println(it?.let { it1 -> convertMillisToDate(it1) })
//                    println(dbHelper.getAllShifts("shift_date",
//                        it?.let { it1 -> convertMillisToDate(it1) }))
                    shifts = dbHelper.getAllShifts("shift_date", it?.let { it1 -> convertMillisToDate(it1) })
                }
            )
        }

        //list current shifts
        Row( modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {

            Column(
                modifier = Modifier.weight(0.2f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "Staff Member Name", style = MaterialTheme.typography.bodySmall)
            }
            Column(
                modifier = Modifier.weight(0.2f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Shift Date", style = MaterialTheme.typography.bodySmall)
            }
            Column(
                modifier = Modifier.weight(0.15f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Start Time", style = MaterialTheme.typography.bodySmall)
            }
            Column(
                modifier = Modifier.weight(0.15f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Status", style = MaterialTheme.typography.bodySmall)
            }
            Column(
                modifier = Modifier.weight(0.2f),
                horizontalAlignment = Alignment.End
            ) {
                Text(text = "Delete", style = MaterialTheme.typography.bodySmall)
            }

        }
        HorizontalDivider()
        LazyColumn() {
            items(items = shifts, itemContent = {item ->  ListShifts(item, dbHelper, navManager) })
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            NavigationBar(navManager, adminNavBarItems, dbHelper)
        }
    }
}