package com.vasant.pillpal.ui.components

import androidx.collection.mutableObjectIntMapOf
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import com.vasant.pillpal.R
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vasant.pillpal.ui.theme.BackgroundColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarHomeScreen(navHostController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = BackgroundColor
            )
    ) {
        IconButton(
            onClick = {}, modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.settingicon),
                tint = Color.Unspecified,
                contentDescription = "Settings Icon",
                modifier = Modifier.size(50.dp)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = {}, Modifier.padding(top = 10.dp, end = 10.dp)) {
            Icon(
                painter = painterResource(R.drawable.notificationbing),
                tint = Color.Unspecified,
                contentDescription = "Settings Icon",
                modifier = Modifier.size(40.dp)
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        IconButton(onClick = {}, modifier = Modifier.padding(top = 10.dp, end = 10.dp)) {
            Icon(
                painter = painterResource(R.drawable.user),
                tint = Color.Unspecified,
                contentDescription = "Search Icon",
                modifier = Modifier.size(50.dp)
            )
        }

    }


//    TopAppBar(
//        colors = TopAppBarDefaults.topAppBarColors(),
//        title ={} ,
//        modifier = Modifier,
//        navigationIcon = {
//
//        },
//        actions = {},
//        expandedHeight = 0.dp,
//    )
}