package com.example.gracetastybites.screens.admin

import android.content.SharedPreferences
import android.icu.text.SimpleDateFormat
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gracetastybites.button.ReusableButton
import com.example.gracetastybites.header.Header
import com.example.gracetastybites.mockData.UserAuth
import com.example.gracetastybites.mockData.adminNavBarItems
import com.example.gracetastybites.navigationBar.NavigationBar
import com.example.gracetastybites.sqllite.DatabaseHelper
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignShit(navManager: NavController, dbHelper: DatabaseHelper, sharedPreferences: SharedPreferences) {
    val bgCream = MaterialTheme.colorScheme.background

    val getAllStaff = dbHelper.getAllUsers("staff")
    val timeOptions = listOf("09:00", "17:00")
//    println(getAllStaff)

    var selectedStaffId by remember { mutableIntStateOf(0) }
    var shiftDate by remember { mutableStateOf<Long?>(null) }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("")}

    val context = LocalContext.current



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgCream),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header(sharedPreferences, navManager)
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)) {
            Text(text = "Assign Shift")
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            ItemDropDown(users = getAllStaff) { staffId ->
                selectedStaffId = staffId
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            DatePickerFieldToModal(
                shiftDate = shiftDate,
                onDateChange = { shiftDate = it }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            TimeDropDown(
                times = timeOptions,
                selectedTime = startTime,
                onTimeSelected = { startTime = it },
                "Select Start Time"
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            TimeDropDown(
                times = timeOptions,
                selectedTime = endTime,
                onTimeSelected = { endTime = it },
                "Select End Time"
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
           ReusableButton("Assign Shift", "", 150, 50, otherCommands = {
               val formatted: String = shiftDate?.let { convertMillisToDate(it) }.toString()

               val createShift = dbHelper.insertShift(selectedStaffId,
                   formatted, startTime, endTime)
               println("This is check for what create shift returns $createShift")
               println(shiftDate?.let { convertMillisToDate(it.toLong()) })



               if (createShift > 0) {
                   Toast.makeText(context, "Shift Assigned Successfully!", Toast.LENGTH_SHORT).show()
                   navManager.navigate("manage-shifts")
               } else {
                   Toast.makeText(context, "Shift Adding Error!", Toast.LENGTH_SHORT).show()
               }
           })
        }
        Spacer(modifier = Modifier.weight(1f))
        NavigationBar(navManager, adminNavBarItems, dbHelper)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDropDown(
    users: List<UserAuth>,
    onStaffSelected: (Int) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("Select a Staff Member") }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = !isExpanded }
    ) {
        TextField(
            modifier = Modifier.menuAnchor(),
            value = selectedText,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
            label = { Text("Staff Member") },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.background,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.16f),
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.16f),

                ),
            singleLine = true,
            textStyle = TextStyle(fontWeight = FontWeight.Medium),
            maxLines = 1
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            users.forEach { user ->
                val fullName = "${user.firstname} ${user.lastname}".trim()
                DropdownMenuItem(
                    text = { Text(fullName.ifEmpty { "Unnamed" }) },
                    onClick = {
                        selectedText = fullName
                        isExpanded = false
                        onStaffSelected(user.id)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerFieldToModal(
    shiftDate: Long?,
    onDateChange: (Long?) -> Unit,
    modifier: Modifier = Modifier
) {
    var showModal by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = shiftDate?.let { convertMillisToDate(it) } ?: "",
        onValueChange = {},
        readOnly = true,
        enabled = true,
        label = { Text("Shift Date", style = TextStyle(fontWeight = FontWeight.Medium)) },
        placeholder = { Text("MM/DD/YYYY") },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.background,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.16f),
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.16f),
        ),
        singleLine = true,
        textStyle = TextStyle(fontWeight = FontWeight.Medium),
        maxLines = 1,
        trailingIcon = {
            Icon(Icons.Default.DateRange, contentDescription = "Select date")
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .pointerInput(Unit) {
                awaitEachGesture {
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (upEvent != null) {
                        showModal = true
                    }
                }
            }
    )


    if (showModal) {
        DatePickerModal(
            onDateSelected = {
                onDateChange(it)
            },
            onDismiss = { showModal = false }
        )
    }
}


fun convertMillisToDate(millis: Long): String? {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeDropDown(
    times: List<String>,
    selectedTime: String,
    onTimeSelected: (String) -> Unit,
    labelName: String
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            modifier = Modifier.menuAnchor(),
            value = selectedTime,
            onValueChange = {},
            readOnly = true,
            placeholder = { Text(text = labelName, style = TextStyle(fontWeight = FontWeight.Medium)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.background,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.16f),
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.16f),

                ),
            singleLine = true,
            textStyle = TextStyle(fontWeight = FontWeight.Medium),
            maxLines = 1
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            times.forEach { time ->
                DropdownMenuItem(
                    text = { Text(text = time, style = TextStyle(fontWeight = FontWeight.Medium)) },
                    onClick = {
                        onTimeSelected(time)
                        expanded = false
                    }
                )
            }
        }
    }
}