package com.vasant.pillpal.ui.screens.AuthScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.vasant.pillpal.R
import com.vasant.pillpal.ui.theme.BackgroundColor
import com.vasant.pillpal.ui.theme.SecondaryContainerColor
import com.vasant.pillpal.ui.theme.rubikFamily

const val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
private fun checkEmail(email: String): Boolean {
    return email.matches(emailRegex.toRegex())
}

@Composable
fun SignIn(navController: NavController) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val isValidate = remember { mutableStateOf(false) }

    val isPasswordShown = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        Box(
            modifier = Modifier.size(430.dp),
        ) {
            Image(
                painter = painterResource(R.drawable.background),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomStart) {


            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = "Sign In",
                    fontFamily = rubikFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp
                )
                HorizontalDivider(
                    Modifier
                        .width(80.dp)
                        .clip(RoundedCornerShape(30.dp)),
                    color = SecondaryContainerColor, thickness = 5.dp
                )
                Spacer(Modifier.height(20.dp))

                Text(
                    text = "Email",
                    fontFamily = rubikFamily,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 10.dp, bottom = 6.dp)
                )
                TextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = null)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .padding(start = 8.dp),
                    placeholder = { Text("I demo@email.com", color = Color.Gray) },
                    isError = isValidate.value,
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SecondaryContainerColor,
                        unfocusedBorderColor = SecondaryContainerColor,
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        focusedTextColor = Color.Black.copy(0.8f),
                        unfocusedTextColor = Color.Black.copy(0.8f)

                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Email
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            isValidate.value = checkEmail(email.value)
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),

                    )



                Text(
                    text = "Password",
                    fontFamily = rubikFamily,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 10.dp, top = 12.dp, bottom = 6.dp)
                )

                TextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = null)
                    },
                    visualTransformation = if (isPasswordShown.value) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = {
                            isPasswordShown.value = !isPasswordShown.value
                        }) {
                            if (isPasswordShown.value) {
                                Icon(
                                    painter = painterResource(R.drawable.eye),
                                    modifier = Modifier.size(24.dp),
                                    contentDescription = null,
                                )
                            } else {
                                Icon(
                                    painter = painterResource(R.drawable.hidden),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }


                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .padding(start = 8.dp),
                    placeholder = { Text("enter your password", color = Color.Gray) },
                    isError = isValidate.value,
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SecondaryContainerColor,
                        unfocusedBorderColor = SecondaryContainerColor,
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        focusedTextColor = Color.Black.copy(0.8f),
                        unfocusedTextColor = Color.Black.copy(0.8f)

                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Email
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            isValidate.value = checkEmail(email.value)
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),

                    )
                Button(
                    onClick = {},
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(40.dp)
                        .fillMaxWidth(),
                    colors = ButtonColors(
                        containerColor = SecondaryContainerColor,
                        contentColor = Color.White,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = Color.Transparent
                    )
                ) {
                    Text(
                        "Sign In",
                        fontFamily = rubikFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Don't Have an Account ?")
                    Button(
                        onClick = {}, colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,

                            )
                    ) {
                        Text(
                            text = " Sign Up",
                            color = SecondaryContainerColor,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = rubikFamily,
                            modifier = Modifier.clickable {
                            }
                        )
                    }
                }

                Spacer(Modifier.height(50.dp))
            }

        }
    }
}