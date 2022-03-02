package com.example.chatapp_by_command.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun LoginCustomOutlinedTextField(entry: String, hint: String, icon: ImageVector, onChange:(String) -> Unit = {}){

    val fullWidthModifier = Modifier.fillMaxWidth().padding(2.dp)

    var text by remember { mutableStateOf("") }
    text = entry

    OutlinedTextField(
        modifier = fullWidthModifier,
        value = text,
        label = { Text(text = hint) },
        onValueChange = {
            text = it
            onChange(it)},
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
        }
    )
}

