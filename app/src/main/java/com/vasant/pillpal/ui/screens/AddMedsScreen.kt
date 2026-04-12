package com.vasant.pillpal.ui.screens

import android.app.AlertDialog
import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.vasant.pillpal.data.db.MedicineEvent
import com.vasant.pillpal.ui.components.AddMedTop
import com.vasant.pillpal.ui.presentation.MedicineState
import com.vasant.pillpal.ui.presentation.MedicineType
import com.vasant.pillpal.ui.theme.BackgroundColor
import com.vasant.pillpal.ui.theme.SecondaryContainerColor
import com.vasant.pillpal.ui.theme.jetbrainFamily
import com.vasant.pillpal.ui.theme.pillColor
import com.vasant.pillpal.ui.viewmodel.MedicineViewModel
import com.vasant.pillpal.utils.ALARM_PERMISSION
import com.vasant.pillpal.utils.NOTIFICATION_PERMISSION
import com.vasant.pillpal.utils.getFormattedTime
import com.vasant.pillpal.utils.getTimeInMillis
import com.vasant.pillpal.utils.hasPermission
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll

@Composable
fun AddMedsScreen(
        navController: NavHostController,
    medicineViewModel: MedicineViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val exactAlarmIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        Intent("android.settings.REQUEST_SCHEDULE_EXACT_ALARM").apply {
            data = Uri.fromParts("package", context.packageName, null)
        }
    } else null
    val notificaionIntent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    ).apply {
        data = Uri.fromParts("package", context.packageName, null)
    }

    Scaffold(
        topBar = { AddMedTop(navController) },
        containerColor = BackgroundColor,
        contentWindowInsets = WindowInsets.safeDrawing,
        bottomBar = {
            // Fixed CTA Save button
            Surface(
                color = BackgroundColor,
                shadowElevation = 8.dp
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Button(
                        onClick = {
                            val needsExactAlarmPermission = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !hasPermission(context, ALARM_PERMISSION)
                            if (needsExactAlarmPermission) {
                                AlertDialog.Builder(context).setTitle("Permission Required")
                                    .setMessage(
                                        "To ensure you receive timely medication reminders Dose-flow needs permission to set precise alarms.Please tap 'Go to Settings' and enable 'Allow setting alarms and reminders' for our app ."
                                    ).setPositiveButton("Go to Settings") { _, _ ->
                                        exactAlarmIntent?.let { context.startActivity(it) }
                                    }.setNegativeButton("Cancel", null)
                                    .show()
                            } else if (!hasPermission(context, NOTIFICATION_PERMISSION)) {
                                AlertDialog.Builder(context).setTitle("Permission Required")
                                    .setMessage(
                                        "To ensure you receive timely medication reminders Dose-flow needs permission to set precise alarms.Please tap 'Go to Settings' and enable 'Allow Notification' for our app ."
                                    ).setPositiveButton("Go to Settings") { _, _ ->
                                        context.startActivity(notificaionIntent)
                                    }.setNegativeButton("Cancel", null)
                                    .show()
                            } else {
                                medicineViewModel.onEvent(MedicineEvent.SaveMedicine(context))
                                navController.popBackStack()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(
                            "Save Medication",
                            fontFamily = jetbrainFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(BackgroundColor)
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            AddMedsScreenPill(
                currentValue = medicineViewModel.state,
                title = "Medicine Name",
                Event = medicineViewModel::onEvent,
            )

            // Structured dosage input
            DosageInputRow(state = medicineViewModel.state, onEvent = medicineViewModel::onEvent)

            // Medicine type selector (wrap chips)
            MedicineTypeSelector(currentState = medicineViewModel.state, onEvent = medicineViewModel::onEvent)

            // Notes input (multiline)
            AddMedsScreenPill(
                title = "Notes",
                Event = medicineViewModel::onEvent,
                currentValue = medicineViewModel.state,
            )


            AddTimePill(medicineViewModel)

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MedicineTypeSelector(
    currentState: MutableStateFlow<MedicineState>,
    onEvent: (MedicineEvent) -> Unit
) {
    val value = currentState.collectAsStateWithLifecycle()
    val selected = value.value.med_type ?: MedicineType.TABLET

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 13.dp),
        colors = CardDefaults.cardColors(containerColor = pillColor)
    ) {
        Column(modifier = Modifier.padding(bottom = 10.dp)) {
            Row(
                modifier = Modifier
                    .padding(start = 15.dp, end = 10.dp, top = 10.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Medicine Type",
                    fontFamily = jetbrainFamily,
                    color = Color.Black.copy(alpha = 0.8f),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
            }
        }
        Card(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 10.dp)
                .align(Alignment.CenterHorizontally),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F7F8))
        ) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MedicineType.entries.forEach { type ->
                    val isSelected = type == selected
                    val bg = if (isSelected) SecondaryContainerColor else Color.White
                    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else Color(0xFFB0B0B0)
                    Surface(
                        shape = CircleShape,
                        color = bg,
                        tonalElevation = if (isSelected) 2.dp else 0.dp,
                        modifier = Modifier
                            .border(BorderStroke(2.dp, borderColor), shape = CircleShape)
                            .clickable { onEvent(MedicineEvent.MedicineTypeChanged(type)) }
                    ) {
                        Text(
                            text = type.name,
                            fontFamily = jetbrainFamily,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (isSelected) Color.White else Color.Black.copy(alpha = 0.8f),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DosageInputRow(
    state: MutableStateFlow<MedicineState>,
    onEvent: (MedicineEvent) -> Unit
) {
    val value = state.collectAsStateWithLifecycle()
    val medicineType = value.value.med_type ?: MedicineType.TABLET

    // Auto-determine unit based on medicine type
    val defaultUnit = when (medicineType) {
        MedicineType.TABLET, MedicineType.CAPSULE-> "tablet"
        MedicineType.SYRUP-> "ml"
        MedicineType.DROPS-> "drops"
        MedicineType.OTHERS-> "mg" // default for other
    }

    // Available units based on medicine type
    val availableUnits = when (medicineType) {
        MedicineType.TABLET, MedicineType.CAPSULE-> listOf("tablet")
        MedicineType.SYRUP-> listOf("ml")
        MedicineType.DROPS-> listOf("drops")
        MedicineType.OTHERS-> listOf("mg", "ml", "g") // flexible for "Other"
    }

    val dosageText = value.value.dosage
    val parts = remember(dosageText, defaultUnit) {
        val tokens = dosageText.trim().split(" ").filter { it.isNotBlank() }
        val amount = tokens.firstOrNull { it.any(Char::isDigit) } ?: ""
        val unit = tokens.drop(1).firstOrNull()?.lowercase() ?: defaultUnit
        amount to unit
    }

    var amount by remember(parts) { mutableStateOf(parts.first) }
    var expanded by remember { mutableStateOf(false) }
    var unit by remember(parts, defaultUnit) {
        mutableStateOf(if (availableUnits.contains(parts.second)) parts.second else defaultUnit)
    }

    // Update unit when medicine type changes
    LaunchedEffect(medicineType) {
        unit = defaultUnit
        val cleanAmount = amount.filter { it.isDigit() || it == '.' }
        val composed = if (cleanAmount.isNotEmpty()) "$cleanAmount $unit" else ""
        onEvent(MedicineEvent.AddDosageChange(composed))
    }

    fun pushDosage() {
        val cleanAmount = amount.filter { it.isDigit() || it == '.' }
        val composed = if (cleanAmount.isNotEmpty()) "$cleanAmount $unit" else ""
        onEvent(MedicineEvent.AddDosageChange(composed))
    }

    Card(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 13.dp),
        colors = CardDefaults.cardColors(containerColor = pillColor)
    ) {
        Column(modifier = Modifier.padding(bottom = 10.dp)) {
            Row(
                modifier = Modifier
                    .padding(start = 15.dp, end = 10.dp, top = 10.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Dosage",
                    fontFamily = jetbrainFamily,
                    color = Color.Black.copy(alpha = 0.8f),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
            }
        }

        Card(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, bottom = 16.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F7F8))
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Amount input
                OutlinedTextField(
                    value = amount,
                    onValueChange = {
                        amount = it.filter { ch -> ch.isDigit() || ch == '.' }
                        pushDosage()
                    },
                    modifier = Modifier.weight(1f),
                    label = { Text("Amount") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )

                // Unit dropdown (only show if multiple options available)
                if (availableUnits.size > 1) {
                    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
                        OutlinedTextField(
                            value = unit,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Unit") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier.menuAnchor().width(IntrinsicSize.Min)
                        )
                        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            availableUnits.forEach { opt ->
                                DropdownMenuItem(
                                    text = { Text(opt) },
                                    onClick = {
                                        unit = opt
                                        expanded = false
                                        pushDosage()
                                    }
                                )
                            }
                        }
                    }
                } else {
                    // Single unit - just show as text
                    OutlinedTextField(
                        value = unit,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Unit") },
                        enabled = false,
                        modifier = Modifier.width(100.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun AddTimePill(medicineViewModel: MedicineViewModel) {
    var showTimePicker by remember { mutableStateOf(false) }
    var time by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .padding(20.dp)
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .background(pillColor)
            .clickable(
                onClick = { showTimePicker = true })
            .background(shape = RoundedCornerShape(13.dp), color = pillColor)
    ) {
        Column {
            Text(
                modifier = Modifier.padding(start = 20.dp, top = 10.dp, bottom = 10.dp),
                text = "Add Time",
                fontFamily = jetbrainFamily,
                fontWeight = FontWeight.Bold,
                color = Color.Black.copy(alpha = 0.8f),
                fontSize = 18.sp
            )
            Card(
                modifier = Modifier
                    .padding(start = 30.dp, end = 30.dp, bottom = 15.dp)

                    .border(
                        width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Row(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(10.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Time: $time",
                        fontFamily = jetbrainFamily,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black.copy(alpha = 0.8f),
                        fontSize = 16.sp,
                        modifier = Modifier.padding(10.dp),
                    )
                }
            }
        }
        if (showTimePicker) {
            AddTimePickerDialog(onConfirm = { hour, minute ->
                val mili = getTimeInMillis(hour, minute)
                time = getFormattedTime(mili)
                medicineViewModel.onEvent(
                    MedicineEvent.DateChanged(
                        date = getTimeInMillis(
                            hour = hour,
                            minute = minute
                        )
                    )
                )
                showTimePicker = false
            }, onDisMiss = {
                showTimePicker = false
            })
        }
    }
}


@Composable

fun AddMedsScreenPill(
    title: String,
    Event: (MedicineEvent) -> Unit,
    currentValue: MutableStateFlow<MedicineState>,
) {
    val value = currentValue.collectAsStateWithLifecycle()
    Card(
        Modifier
            .fillMaxWidth()
            .padding(13.dp), colors = CardDefaults.cardColors(
            containerColor = pillColor
        )
    ) {
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
            val isNotes = title == "Notes"
            TextField(
                value = when (title) {
                    "Medicine Name" -> value.value.medicineName
                    "Dosage" -> value.value.dosage
                    "Notes" -> value.value.note ?: ""
                    else -> ""
                },
                onValueChange = {
                    when (title) {
                        "Medicine Name" -> Event(MedicineEvent.MedicineNameChanged(medicineName = it))
                        "Dosage" -> Event(MedicineEvent.AddDosageChange(dosage = it))
                        "Notes" -> Event(MedicineEvent.NoteChanged(note = it))
                    }
                },
                singleLine = !isNotes,
                minLines = if (isNotes) 3 else 1,
                textStyle = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.background(Color.White),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
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
                }
            )
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
            color = Color.Black.copy(alpha = 0.8f),
            fontWeight = FontWeight.W600,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 30.dp, end = 30.dp, top = 15.dp, bottom = 15.dp)
        )

    }
}