package com.vasant.pillpal.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vasant.pillpal.R
import com.vasant.pillpal.ui.navigation.MainUiRoute
import com.vasant.pillpal.ui.navigation.NavigationRoute
import com.vasant.pillpal.ui.theme.BackgroundColor

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val isSelected by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                BackgroundColor
            )
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {}) {
            Box() {
                Image(
                    painter = painterResource(R.drawable.home),
                    contentDescription = "Home Icon",
                    modifier = Modifier.size(30.dp), contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(Color.Gray)
                )

            }
        }
        Spacer(Modifier.weight(1f))
        IconButton(onClick = { navController.navigate(MainUiRoute.AddMedicineScreen) }) {
            Box() {
                Icon(
                    painter = painterResource(R.drawable.add),
                    tint = Color.Unspecified,
                    contentDescription = "Add Icon",
                    modifier = Modifier.size(40.dp)
                )
            }
        }
        Spacer(Modifier.weight(1f))
        IconButton(onClick = {}) {
            Box {
                Image(
                    painter = painterResource(R.drawable.guides),
                    contentDescription = "Home Icon",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}