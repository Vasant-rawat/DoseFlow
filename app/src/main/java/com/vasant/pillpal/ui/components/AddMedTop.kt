package com.vasant.pillpal.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.vasant.pillpal.R
import com.vasant.pillpal.ui.navigation.NavigationRoute
import com.vasant.pillpal.ui.theme.BackgroundColor
import com.vasant.pillpal.ui.theme.jetbrainFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedTop(
    navController: NavHostController
) {
    CenterAlignedTopAppBar(
        title = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Add",
                    fontFamily = jetbrainFamily,
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Medication",
                    fontWeight = FontWeight.Medium,
                    fontFamily = jetbrainFamily,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = { navController.navigate(NavigationRoute.HomeScreen) }) {
                Icon(
                    painter = painterResource(id = R.drawable.backarrow),
                    contentDescription = "Back Icon"
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = BackgroundColor
        )
    )

}