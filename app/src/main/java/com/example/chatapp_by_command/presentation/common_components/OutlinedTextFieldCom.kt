package com.example.chatapp_by_command.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun OutlinedTextFieldNameCom(
    entry: String,
    hint: String,
    onChange:(String) -> Unit = {},
    onFocusChange:(Boolean) -> Unit = {}){

    var isNameChange by remember { mutableStateOf(false) }
    var isFocusChange by remember { mutableStateOf(false) }


    var text by remember { mutableStateOf("") }
    text = entry

    val fullWidthModifier = Modifier
        .fillMaxWidth()
        .padding(2.dp)
        .onFocusChanged {

            if (isNameChange) {
                isFocusChange = true
                onFocusChange(isFocusChange)
            }
        }

    OutlinedTextField(modifier = fullWidthModifier,
        value = text,
        label = {Text(hint)},
        onValueChange = {
            text = it
            onChange(it)
            isNameChange = true
        })
}

@Composable
fun OutlinedTextFieldSurnameCom(
    entry: String,
    hint: String,
    onChange:(String) -> Unit = {},
    onFocusChange:(Boolean) -> Unit = {}){

    var isNameChange by remember { mutableStateOf(false) }
    var isFocusChange by remember { mutableStateOf(false) }

    var text by remember { mutableStateOf("") }
    text = entry

    val fullWidthModifier = Modifier
        .fillMaxWidth()
        .padding(2.dp)
        .onFocusChanged {
            if (isNameChange) {
                isFocusChange = true
                onFocusChange(isFocusChange)
            }
        }

    OutlinedTextField(modifier = fullWidthModifier,
        value = text,
        label = {Text(hint)},
        onValueChange = {
            text = it
            onChange(it)
            isNameChange = true
        })
}
