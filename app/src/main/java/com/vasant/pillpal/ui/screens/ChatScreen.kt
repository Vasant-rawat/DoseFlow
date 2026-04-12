package com.vasant.pillpal.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.vasant.pillpal.R
import com.vasant.pillpal.data.chat.ChatUiModel
import com.vasant.pillpal.ui.theme.BackgroundColor
import com.vasant.pillpal.ui.theme.SecondaryContainerColor
import com.vasant.pillpal.ui.theme.fontColor
import com.vasant.pillpal.ui.theme.jetbrainFamily
import com.vasant.pillpal.ui.viewmodel.ChatViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navHost: NavHostController) {
    val viewModel: ChatViewModel = hiltViewModel()
    val conversation by viewModel.conversation.collectAsState()
    val listState = rememberLazyListState()
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(conversation.size) {
        if (conversation.isNotEmpty()) {
            listState.animateScrollToItem(conversation.size - 1)
        }
    }

    Scaffold(
        topBar = {
            ChatTopBar(onBackClick = { navHost.popBackStack() })
        },
        containerColor = BackgroundColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
                .padding(paddingValues)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = conversation,
                    key = { it.hashCode() }
                ) { message ->
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(animationSpec = tween(300)) +
                                slideInVertically(animationSpec = tween(300))
                    ) {
                        MessageBubble(message)
                    }
                }

                if (isLoading) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = SecondaryContainerColor.copy(alpha = 0.15f),
                                modifier = Modifier
                                    .size(32.dp)
                                    .align(Alignment.Bottom)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.SmartToy,
                                        contentDescription = null,
                                        tint = SecondaryContainerColor,
                                        modifier = Modifier.padding(6.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            TypingIndicator()
                        }
                    }
                }
            }

            ChatInputBox(
                onSendMessage = { message ->
                    isLoading = true
                    viewModel.sendMessage(message)
                    isLoading = false
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopBar(onBackClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = fontColor
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = SecondaryContainerColor.copy(alpha = 0.15f),
                modifier = Modifier.size(44.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.SmartToy,
                        contentDescription = "AI Assistant",
                        tint = SecondaryContainerColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = "Medical Assistant",
                    fontFamily = jetbrainFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = fontColor
                )
                Text(
                    text = "AI-powered health advisor",
                    fontFamily = jetbrainFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = fontColor.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Composable
fun MessageBubble(message: ChatUiModel.Message) {
    val isFromUser = message.isFromMe

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = if (isFromUser) Arrangement.End else Arrangement.Start
    ) {
        if (!isFromUser) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = SecondaryContainerColor.copy(alpha = 0.15f),
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.Bottom)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.SmartToy,
                        contentDescription = null,
                        tint = SecondaryContainerColor,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        Surface(
            modifier = Modifier.widthIn(max = 280.dp),
            shape = RoundedCornerShape(
                topStart = if (isFromUser) 20.dp else 4.dp,
                topEnd = if (isFromUser) 4.dp else 20.dp,
                bottomStart = 20.dp,
                bottomEnd = 20.dp
            ),
            color = if (isFromUser)
                SecondaryContainerColor
            else
                MaterialTheme.colorScheme.surface,
            shadowElevation = 2.dp
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                color = if (isFromUser)
                    Color.White
                else
                    fontColor,
                fontFamily = jetbrainFamily,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }

        if (isFromUser) {
            Spacer(modifier = Modifier.width(8.dp))
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = SecondaryContainerColor.copy(alpha = 0.15f),
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.Bottom)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        painter = painterResource(R.drawable.user),
                        contentDescription = null,
                        tint = SecondaryContainerColor,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ChatInputBox(
    onSendMessage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var input by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val isInputEmpty = input.isBlank()

    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text(
                        "Ask me anything about medications...",
                        fontFamily = jetbrainFamily,
                        fontSize = 14.sp,
                        color = fontColor.copy(alpha = 0.4f)
                    )
                },
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SecondaryContainerColor.copy(alpha = 0.5f),
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    cursorColor = SecondaryContainerColor,
                    focusedTextColor = fontColor,
                    unfocusedTextColor = fontColor
                ),
                maxLines = 4,
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontFamily = jetbrainFamily,
                    fontSize = 14.sp
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            FilledIconButton(
                onClick = {
                    if (input.isNotBlank()) {
                        onSendMessage(input)
                        input = ""
                        focusManager.clearFocus()
                    }
                },
                enabled = !isInputEmpty,
                modifier = Modifier.size(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = SecondaryContainerColor,
                    contentColor = Color.White,
                    disabledContainerColor = SecondaryContainerColor.copy(alpha = 0.3f),
                    disabledContentColor = Color.White.copy(alpha = 0.5f)
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send message",
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}

@Composable
fun TypingIndicator() {
    Row(
        modifier = Modifier.padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        repeat(3) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        color = SecondaryContainerColor,
                        shape = CircleShape
                    )
            )
        }
    }
}