package com.vasant.pillpal.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.vasant.pillpal.ui.components.HomeContent
import com.vasant.pillpal.ui.components.TopBarHomeScreen

@Preview
@Composable
fun HomeScreen(){
    Scaffold (topBar = { TopBarHomeScreen() }
        , bottomBar = {}
    ){
        HomeContent(it)
    }
}





