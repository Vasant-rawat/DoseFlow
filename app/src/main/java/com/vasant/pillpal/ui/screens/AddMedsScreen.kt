package com.vasant.pillpal.ui.screens

import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.vasant.pillpal.ui.theme.BackgroundColor
import com.vasant.pillpal.ui.theme.SecondaryContainerColor
import com.vasant.pillpal.ui.theme.jetbrainFamily
import com.vasant.pillpal.ui.theme.pillColor

@Preview
@Composable
fun AddMedsScreen() {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(BackgroundColor)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            AddMedsScreenPill(title = "Medicine Name")
            AddMedsScreenPill(title = "Dosage")
            AddTimePill()
            Row {
                CustomButton(title = "Cancel", onClick = {}, Color.Transparent)
                Spacer(modifier = Modifier.weight(1f))
                CustomButton(title = "Save", onClick = {}, SecondaryContainerColor)
            }
        }
    }
}

@Composable
fun AddTimePill(
) {
    var showTimePicker by remember { mutableStateOf(false) }
    var Time by remember { mutableStateOf(" ") }
    Box(
        modifier = Modifier
            .padding(20.dp)
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .background(pillColor)
            .clickable(
                onClick = { showTimePicker = true }
            )
            .background(shape = RoundedCornerShape(13.dp), color = pillColor)) {
        Column {
            Text(
                modifier = Modifier.padding(start = 20.dp, top = 10.dp, bottom = 10.dp),
                text = "Add Time",
                fontFamily = jetbrainFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Card(
                modifier = Modifier
                    .padding(start = 30.dp, end = 30.dp, bottom = 15.dp)

                    .border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Row(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(10.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Time: $Time",
                        fontFamily = jetbrainFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(10.dp),
                    )
                }
            }
        }
        if (showTimePicker) {
            AddTimePickerDialog(
                onConfirm = { hour, minute ->
                    Time = String.format("%02d:%02d", hour, minute)
                    showTimePicker = false
                },
                onDisMiss = {
                    showTimePicker = false
                }
            )
        }
    }
}


@Composable

fun AddMedsScreenPill(
    title: String,
) {
    var value by remember { mutableStateOf("") }
    Card(
        Modifier
            .fillMaxWidth()
            .padding(13.dp), colors = CardDefaults.cardColors(
            containerColor = pillColor
        )
    ) {
        // Show the time picker dialog if Addtime is true
        Column(modifier = Modifier.padding(bottom = 10.dp)) {

            Row(
                modifier = Modifier
                    .padding(start = 15.dp, end = 10.dp, top = 10.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = title,
                    fontFamily = jetbrainFamily,
                    modifier = Modifier,
                    color = Color.Black.copy(alpha = 0.8f),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
            }
        }
        Card(
            modifier = Modifier
                .padding(bottom = 20.dp)
                .border(
                    width = 0.dp, color = Color(0xFFB0B0B0), shape = RoundedCornerShape(8.dp)
                )
                .align(Alignment.CenterHorizontally),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F7F8))
        ) {
            TextField(
                value,
                onValueChange = { value = it },
                textStyle = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .background(Color.White),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    cursorColor = Color(0xFF000000),
                    disabledIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    Text(
                        text = "Enter $title",
                        fontFamily = jetbrainFamily,
                        color = Color(0xFFB0B0B0)
                    )
                })
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTimePickerDialog(
    onConfirm: (hour: Int, minute: Int) -> Unit = { _, _ -> },
    onDisMiss: () -> Unit,
) {
    val currentTime = Calendar.getInstance()
    val pickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = false
    )
    Dialog(onDismissRequest = { onDisMiss() }) {
        Surface(shape = MaterialTheme.shapes.extraLarge, tonalElevation = 10.dp) {
            Column {
                TimePicker(state = pickerState)
                Row(
                    modifier = Modifier
                        .width(IntrinsicSize.Min)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = { onDisMiss() }) { Text("Cancel") }
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(onClick = {
                        onConfirm(
                            pickerState.hour, pickerState.minute
                        )
                    }) { Text("OK") }
                }

            }
        }
    }
}


@Composable
fun CustomButton(title: String, onClick: () -> Unit, color: Color) {
    Card(
        modifier = Modifier
            .padding(34.dp)
            .border(
                width = 1.dp, shape = RoundedCornerShape(11.dp), color = Color.Gray
            )
            .clickable {
                onClick()
            }, colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Text(
            text = title,
            fontFamily = jetbrainFamily,
            fontWeight = FontWeight.W600,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 30.dp, end = 30.dp, top = 15.dp, bottom = 15.dp)
        )

    }
}