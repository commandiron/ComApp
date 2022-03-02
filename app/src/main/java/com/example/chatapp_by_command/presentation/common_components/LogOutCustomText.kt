package com.example.chatapp_by_command.presentation.common_components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LogOutCustomText(
    entry: String = "Log Out",
    fontSize: TextUnit = 10.sp,
    onClick:() -> Unit = {}
) {

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(percent = 50))
            .background(
                MaterialTheme.colors.surface
            )
            .height(26.dp)
            .fillMaxWidth()
            .clickable {
                onClick()
            }) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = entry,
            fontSize = fontSize,
            color = Color.Red
        )
    }
}