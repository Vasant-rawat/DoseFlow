package com.vasant.pillpal.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vasant.pillpal.R
import com.vasant.pillpal.data.db.Medicine
import com.vasant.pillpal.ui.theme.BackgroundColor
import com.vasant.pillpal.ui.theme.SecondaryContainerColor
import com.vasant.pillpal.ui.theme.jetbrainFamily
import com.vasant.pillpal.ui.viewmodel.MedicineViewModel

@Composable
fun HomeContent(padding: PaddingValues, medicineViewModel: MedicineViewModel =hiltViewModel()) {
    val medicine = medicineViewModel.meds.collectAsStateWithLifecycle()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(color = BackgroundColor)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            GreetingSection()
            MedicineDisplaySection(medicine)
        }
    }
}

@Composable
fun GreetingSection() {
    Box(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column {
            Text(
                text = "Hi!",
                fontFamily = jetbrainFamily,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Vasant", fontFamily = jetbrainFamily, fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )

        }
        Column {
            Icon(
                painter = painterResource(R.drawable.medicine),
                tint = Color.Unspecified,
                contentDescription = "Medicine Icon",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 70.dp)
                    .size(369.dp)
            )
        }
    }
}

@Composable
fun MedicineDisplaySection(medicine: State<List<Medicine>>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp), colors = CardDefaults.cardColors(
            containerColor = SecondaryContainerColor
        )

    ) {
        Text(
            "Daily Reviews",
            fontFamily = jetbrainFamily,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(10.dp),
            style = MaterialTheme.typography.titleLarge
        )

        if (medicine.value.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Icon or Illustration
                Icon(
                    imageVector = Icons.Outlined.Send,
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .padding(bottom = 16.dp),
                    tint = MaterialTheme.colorScheme.primary
                )

                // Title
                Text(
                    text = "No Medicines Available",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Subtitle
                Text(
                    text = "Add a medicine to start tracking your schedule.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Call-to-Action Button
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text("Add Medicine")
                }
            }
        } else {
            LazyColumn {
                items(medicine.value, key = { medicine -> medicine.id }) {
                    MedicinepillSection(
                        date = it.date,
                        medicineName = it.medName,
                        isCompleted = it.isCompleted
                    )
                }
            }
        }
    }
}


@Composable
fun MedicinepillSection(
    date: String, medicineName: String,
    isCompleted: Boolean = false,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .size(60.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD9D9D9)),
        elevation = CardDefaults.elevatedCardElevation(3.dp)
    ) {
        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {

            Icon(
                painter = painterResource(R.drawable.framemedicine),
                contentDescription = "Medicine Icon",
                modifier = Modifier.size(20.dp), tint = Color.Unspecified
            )
            Column {
                Text(
                    text = medicineName,
                    modifier = Modifier.padding(start = 10.dp),
                    color = Color.Gray.copy(0.7f),
                    fontSize = 12.sp
                )
                Row(modifier = Modifier.padding(start = 10.dp)) {
                    Text(
                        text = date,
                        modifier = Modifier.padding(),
                        fontSize = 12.sp,
                        color = Color.Gray.copy(0.7f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(Modifier.width(20.dp))
                    Text(
                        text = ".",
                        textAlign = TextAlign.Center,
                        color = Color.Gray.copy(0.7f),
                    )
                    Spacer(Modifier.width(50.dp))
                    Text(
                        text = if (isCompleted) "Completed" else "Pending",
                        textAlign = TextAlign.Center,
                        color = Color.Gray.copy(0.7f),
                        modifier = Modifier.padding(end = 10.dp),
                        fontSize = 12.sp,
                    )
                }
            }
            IconButton(onClick = onClick) {
                Icon(
                    painter = painterResource(R.drawable.arrow),
                    contentDescription = "",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(30.dp),
                )
            }
        }

    }
}