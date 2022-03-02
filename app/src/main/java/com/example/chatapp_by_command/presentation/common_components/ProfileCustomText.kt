package com.example.chatapp_by_command.presentation.common_components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileCustomText(
    text: String,
    modifier: Modifier,
){
    Text(
        modifier = modifier,
        fontSize = 10.sp,
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
        text = text)
}