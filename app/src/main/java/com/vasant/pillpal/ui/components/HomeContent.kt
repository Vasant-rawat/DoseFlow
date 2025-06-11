package com.vasant.pillpal.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vasant.pillpal.R
import com.vasant.pillpal.ui.theme.BackgroundColor
import com.vasant.pillpal.ui.theme.SecondaryContainerColor
import com.vasant.pillpal.ui.theme.jetbrainFamily

@Composable
fun HomeContent(padding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(color = BackgroundColor)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            GreetingSection()
            MedicineDislaySection()
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

@Preview
@Composable
fun MedicineDislaySection() {
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
        MedicinepillSection(
            date = "2023-10-01",
            medicineName = "Paracetamol",
            isCompleted = true
        )

        MedicinepillSection(
            date = "2023-10-01",
            medicineName = "Paracetamol",
            isCompleted = true
        )

        MedicinepillSection(
            date = "2023-10-01",
            medicineName = "Paracetamol",
            isCompleted = true
        )

        MedicinepillSection(
            date = "2023-10-01",
            medicineName = "Paracetamol",
            isCompleted = true
        )
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
                    modifier = Modifier.size(30.dp),
                )
            }
        }

    }
}