package com.example.chatapp_by_command.presentation

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun AlertDialogCustomOutlinedTextField(
    entry: String,
    hint: String,
    onChange:(String) -> Unit = {},
    onFocusChange:(Boolean) -> Unit = {}){

    var isNameChange by remember { mutableStateOf(false) }
    var isFocusChange by remember { mutableStateOf(false) }


    var text by remember { mutableStateOf("") }
    text = entry

    //Text Field'ın yüksekliğini azaltamadım. Basic Text Field'a çevrilebilir.
    val fullWidthModifier = Modifier
        .fillMaxWidth()
        .padding(2.dp)
        .onFocusChanged {

            if (isNameChange) {
                isFocusChange = true
                onFocusChange(isFocusChange)
            }
        }

    OutlinedTextField(
        modifier = fullWidthModifier,
        singleLine = true,
        value = text,
        label = {Text(hint)},
        onValueChange = {
            text = it
            onChange(it)
            isNameChange = true
        })
}
