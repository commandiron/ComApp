package com.example.chatapp_by_command.presentation.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.chatapp_by_command.presentation.bottomnavigation.BottomNavItem
import com.example.chatapp_by_command.ui.theme.*
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        )
    )

    val systemUiController = rememberSystemUiController()

    LaunchedEffect(key1 = true) {
        systemUiController.setNavigationBarColor(color = Black, darkIcons = true)
        systemUiController.setStatusBarColor(color = Black, darkIcons = true)

        startAnimation = true
        delay(4000)

        navController.popBackStack()
        navController.navigate(BottomNavItem.SignIn.fullRoute)
    }
    Splash(alpha = alphaAnim.value)
}

@Composable
fun Splash(alpha: Float) {
    Box(
        modifier = Modifier
            .background(Black)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column() {
            Icon(
                modifier = Modifier
                    .size(120.dp)
                    .alpha(alpha = alpha),
                imageVector = Icons.Default.Chat,
                contentDescription = "Logo Icon",
                tint = MaterialTheme.colors.onPrimary
            )
            Text(text ="ComApp",
                color = MaterialTheme.colors.onPrimary,
                fontFamily = FontFamily.Cursive,
                fontSize = 36.sp,
                modifier = Modifier
                    .padding(2.dp, 2.dp, 2.dp, 2.dp)
                    .alpha(alpha = alpha))
        }
    }
}