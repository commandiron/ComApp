package com.example.chatapp_by_command.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

private val DarkColorPalette = darkColors(
    primary = primaryColorDark,
    primaryVariant = primaryVariantColorDark,
    secondary = secondaryColorDark,
    secondaryVariant = secondaryVariantDark,

    background = backgroundColorDark,
    surface = surfaceDark ,
    onPrimary = onPrimaryDark,
    onSecondary = onSecondaryDark,
    onBackground = onBackgroundDark,
    onSurface = onSurfaceDark
)


private val LightColorPalette = lightColors(
    primary = primaryColorLight,
    primaryVariant = primaryVariantColorLight,
    secondary = secondaryColorLight,
    secondaryVariant = secondaryVariantLight,

    background = backgroundColorLight,
    surface = surfaceLight,
    onPrimary = onPrimaryLight,
    onSecondary = onSecondaryLight,
    onBackground = onBackgroundLight,
    onSurface = onSurfaceLight,

)

@Composable
fun ChatApp_by_commandTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {

    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    val textColor = if(darkTheme){
        Color(0xFFC9CED1)
    }else{
        Color.Black
    }

    val systemUiController = rememberSystemUiController()
    var firstTimeSplashDelayForSetSystemBarColors by rememberSaveable { mutableStateOf(true)}
    LaunchedEffect(key1 = darkTheme){
        if(firstTimeSplashDelayForSetSystemBarColors){
            delay(5000)
            firstTimeSplashDelayForSetSystemBarColors = false
        }
        if(darkTheme){
            systemUiController.setStatusBarColor(color = backgroundColorDark)
            systemUiController.setNavigationBarColor(primaryColorDark)
        }else{
            systemUiController.setStatusBarColor(color = backgroundColorLight)
            systemUiController.setNavigationBarColor(primaryColorLight)
        }
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = {
            ProvideTextStyle(
                value = TextStyle(color = textColor),
                content = content
            )
        }
    )
}