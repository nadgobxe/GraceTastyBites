package com.example.gracetastybites.screens.admin

import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gracetastybites.R
import com.example.gracetastybites.button.ReusableButton
import com.example.gracetastybites.header.Header
import com.example.gracetastybites.mockData.Shift
import com.example.gracetastybites.mockData.adminNavBarItems
import com.example.gracetastybites.navigationBar.NavigationBar
import com.example.gracetastybites.sqllite.DatabaseHelper
import com.example.gracetastybites.ui.theme.SemiBoldLabelLarge

@Composable
fun ViewShifts(navManager: NavController, dbHelper: DatabaseHelper, sharedPreferences: SharedPreferences) {
    val bgCream = MaterialTheme.colorScheme.background


    val getShifts = dbHelper.getAllShifts()
    println(getShifts)



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgCream),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header(sharedPreferences,navManager)

        Row(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
            Text(text = "View All Shifts", style = MaterialTheme.typography.bodyLarge)
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            ReusableButton("Assign Shift", "assign-shift", 150, 50, navManager)
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            ReusableButton("Filter by Date", "filter-date", 150, 50, navManager)
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            ReusableButton("Shifts per Employee", "shifts-employee", 170, 50, navManager)
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
            items(items = getShifts, itemContent = {item ->  ListShifts(item, dbHelper, navManager) })
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

@Composable
fun ListShifts(item: Shift, dbHelper: DatabaseHelper, navManager: NavController) {

    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth().padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            modifier = Modifier.weight(0.2f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            val getStaffMember = dbHelper.getUserById(item.staffId)
            Text(
                text = getStaffMember?.firstname.toString() + " " + getStaffMember?.lastname.toString(),
                style = MaterialTheme.typography.bodySmall
            )
        }
        Column(
            modifier = Modifier.weight(0.2f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = item.shiftDate.toString(), style = MaterialTheme.typography.bodySmall)
        }
        Column(
            modifier = Modifier.weight(0.15f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = item.startTime.toString(), style = MaterialTheme.typography.bodySmall)
        }
        Column(
            modifier = Modifier.weight(0.15f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = item.status.toString(), style = MaterialTheme.typography.bodySmall)
        }
        Column(
            modifier = Modifier.weight(0.3f),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .height(40.dp)
                    .clickable {
                        showDialog = true
                    }

            )

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Confirm Delete") },
                    text = { Text("Are you sure that you want to delete this shift") },
                    confirmButton = {
                        Text(
                            text = "Delete",
                            modifier = Modifier.clickable {
                                Toast.makeText(
                                    context,
                                    "Shift deleted successfully!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                dbHelper.deleteShiftById(item.id)
                                navManager.navigate("manage-shifts")
                                showDialog = false
                            },
                            color = Color.Red
                        )
                    },
                    dismissButton = {
                        Text(
                            text = "Cancel",
                            modifier = Modifier.clickable {
                                showDialog = false
                            }
                        )
                    }
                )
            }

        }
    }
        HorizontalDivider()
    }
