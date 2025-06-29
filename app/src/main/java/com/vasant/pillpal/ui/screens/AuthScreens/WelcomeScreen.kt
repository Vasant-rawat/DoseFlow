package com.vasant.pillpal.ui.screens.AuthScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vasant.pillpal.R
import com.vasant.pillpal.ui.theme.BackgroundColor
import com.vasant.pillpal.ui.theme.SecondaryContainerColor
import com.vasant.pillpal.ui.theme.fontColor
import com.vasant.pillpal.ui.theme.rubikFamily

@Preview
@Composable
fun WelcomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundColor),
    ) {

        Column {
            //Box(contentAlignment = Alignment.BottomEnd, modifier = Modifier) {
            Image(
                painter = painterResource(R.drawable.background),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
            )
            //}
            Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
                Text(
                    text = "Welcome",
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp,
                    fontFamily = rubikFamily
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Track your Medicine and never Miss a dose. " +
                            "PillPal helps you manage your medication schedule, sends timely reminders," +
                            " and ensures you stay on top of your health journey with minimal effort.",
                    color = fontColor.copy(0.6f),
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    lineHeight = 18.sp,
                    fontFamily = rubikFamily
                )
                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.End),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Spacer(Modifier.width(200.dp))
                    Text(
                        text = "Continue",
                        fontFamily = rubikFamily,
                        fontSize = 14.sp,
                        color = fontColor.copy(0.9f)
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(30.dp))
                            .background(
                                SecondaryContainerColor
                            )
                    ) {
                        Image(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null
                        )
                    }
                }

            }


        }
    }

}
