package com.example.gracetastybites.screens.admin

import android.content.Context
import android.content.SharedPreferences
import android.icu.text.SimpleDateFormat
import android.print.PrintAttributes
import android.print.PrintManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.gracetastybites.button.ReusableButton
import com.example.gracetastybites.header.Header
import com.example.gracetastybites.mockData.Payroll
import com.example.gracetastybites.mockData.Shift
import com.example.gracetastybites.mockData.UserAuth
import com.example.gracetastybites.mockData.adminNavBarItems
import com.example.gracetastybites.navigationBar.NavigationBar
import com.example.gracetastybites.sqllite.DatabaseHelper
import java.util.Date
import java.util.Locale
import kotlin.reflect.typeOf

@Composable
fun ShiftsPerEmployee(navManager: NavController, dbHelper: DatabaseHelper, sharedPreferences: SharedPreferences) {
    val bgCream = MaterialTheme.colorScheme.background

    val getAllStaff = dbHelper.getAllUsers("staff")
    var selectedStaffId by remember { mutableIntStateOf(0) }

    var startDate by remember { mutableStateOf<Long?>(null) }
    var endDate by remember { mutableStateOf<Long?>(null) }
    var completedShifts: Int by remember { mutableIntStateOf(0) }



    var payroll by remember {
        mutableStateOf(
            Payroll(
                id = 0,
                staffId = 0,
                totalShiftsWorked = 0,
                payPerShift = 0.0,
                totalEarnings = 0.0,
                fileId = ""
            )
        )
    }

    val context = LocalContext.current
    var webView:WebView? = null

    fun htmlContent(payroll: Payroll): String {
        return """
    <html>
    <head>
        <style>
            body { font-family: Arial, sans-serif; padding: 20px; }
            h2 { text-align: center; }
            table { width: 100%; border-collapse: collapse; margin-top: 20px; }
            td { padding: 8px; }
            .label { font-weight: bold; width: 50%; }
            .value { text-align: right; }
            .footer { margin-top: 40px; font-size: 12px; text-align: center; color: #555; }
        </style>
    </head>
    <body>
        <h2>Payroll Receipt</h2>
        <table>
            <tr>
                <td class="label">Employee ID:</td>
                <td class="value">${payroll.staffId}</td>
            </tr>
            <tr>
                <td class="label">Total Shifts Worked:</td>
                <td class="value">${payroll.totalShiftsWorked}</td>
            </tr>
            <tr>
                <td class="label">Pay per Shift:</td>
                <td class="value">£${"%.2f".format(payroll.payPerShift)}</td>
            </tr>
            <tr>
                <td class="label">Total Earnings:</td>
                <td class="value">£${"%.2f".format(payroll.totalEarnings)}</td>
            </tr>
        </table>
        <div class="footer">
            Generated on ${SimpleDateFormat("dd/MM/yyyy", Locale.UK).format(Date())}
        </div>
    </body>
    </html>
"""
    }


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
            Text(text = "Select Range", style = MaterialTheme.typography.bodyLarge)
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            DatePickerFieldToModal(
                shiftDate = startDate,
                onDateChange = {
                      startDate = it
                }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            DatePickerFieldToModal(
                shiftDate = endDate,
                onDateChange = {
                    endDate = it
                }
            )
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
            ReusableButton("Check", "", 120, 50, otherCommands = {
                val formattedStartDate = startDate?.let { convertMillisToDate(it) }
                val formattedEndDate = endDate?.let { convertMillisToDate(it) }
//                println("Type of formattedEndDate is ${formattedEndDate!!::class.simpleName} - value: $formattedEndDate")
//                println("Type of formattedEndDate is ${formattedStartDate!!::class.simpleName} - value: $formattedStartDate")
                if (formattedStartDate != null && formattedEndDate != null) {
                completedShifts = dbHelper.getCompletedShifts(selectedStaffId.toInt(), formattedStartDate,formattedEndDate)}
                println(completedShifts)
                payroll = (Payroll(id = 0, staffId = selectedStaffId, totalShiftsWorked = completedShifts, payPerShift = 96.00, totalEarnings = completedShifts * 96.00, fileId = ""))
            })
        }
        if(completedShifts > 0) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(20.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                var userDetails: UserAuth? = dbHelper.getUserById(selectedStaffId)

                val formattedStartDate = startDate?.let { convertMillisToDate(it) }
                val formattedEndDate = endDate?.let { convertMillisToDate(it) }
                val amount = completedShifts * 96
                val formattedAmount = String.format("%.2f", amount.toDouble())

                Row(
                    modifier = Modifier.fillMaxWidth().padding(20.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "${userDetails?.firstname} ${userDetails?.lastname} managed to complete ${completedShifts} shits between ${formattedStartDate} and ${formattedEndDate} generating a total amount of £${formattedAmount}")

                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(20.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                ReusableButton("Generate Invoice", "", 170, 50, navManager, otherCommands = {
                    exportAsPdf(webView = webView, context = context, payroll = payroll, dbHelper)
                })
                AndroidView(
                    factory = { context ->
                        WebView(context)
                            .apply {
                                webViewClient = WebViewClient()
                                loadDataWithBaseURL(null, htmlContent(payroll), "text/html", "UTF-8", null)
                            }
                    },
                ) {
                    webView = it
                    it.webViewClient = WebViewClient()
                    it.loadDataWithBaseURL(null, htmlContent(payroll), "text/html", "UTF-8", null)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

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

fun exportAsPdf(webView: WebView?, context: Context, payroll: Payroll, dbHelper: DatabaseHelper) {
    if (webView != null) {
        val fileName = "${payroll.staffId}${payroll.totalShiftsWorked}${payroll.totalEarnings}${dbHelper.getUserById(payroll.staffId)?.firstname}${dbHelper.getUserById(payroll.staffId)?.lastname}${Date()}"
        val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager
        val printAdapter =
            webView.createPrintDocumentAdapter(fileName)
        val printAttributes = PrintAttributes.Builder()
            .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
            .build()
        printManager.print(fileName,
            printAdapter,
            printAttributes)
    }
}