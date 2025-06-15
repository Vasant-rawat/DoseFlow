package com.vasant.pillpal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.vasant.pillpal.ui.screens.AddMedsScreen
import com.vasant.pillpal.ui.screens.AddMedsScreenPill
import com.vasant.pillpal.ui.screens.HomeScreen
import com.vasant.pillpal.ui.theme.PillPalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PillPalTheme {
                PillPalApp()
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PillPalTheme {
        PillPalApp()
    }
}