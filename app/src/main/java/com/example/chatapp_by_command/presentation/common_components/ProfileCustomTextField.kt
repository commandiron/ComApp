package com.example.chatapp_by_command.presentation.common_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileCustomTextField(
    entry: String,
    hint: String,
    onChange:(String) -> Unit = {},
    onFocusChange:(Boolean) -> Unit = {},
    fontSize: TextUnit = 10.sp,
    maxLine: Int = 1,
    maxChar: Int = 50,
    keyboardType: KeyboardType = KeyboardType.Text
) {

    var isNameChange by remember { mutableStateOf(false) }
    var isFocusChange by remember { mutableStateOf(false) }

    var text by remember{ mutableStateOf("") }
    text = entry

    BasicTextField(
        modifier = Modifier
            .background(
                MaterialTheme.colors.surface,
                RoundedCornerShape(percent = 50)
            )
            .padding(6.dp)
            .height(20.dp)
            .fillMaxWidth()
            .onFocusChanged {
                if (isNameChange) {
                    isFocusChange = true
                    onFocusChange(isFocusChange)
                }
            },
        value = text,
        onValueChange = {
            if(it.length <= maxChar){
                text = it
                onChange(it)
                isNameChange = true
            }
        },
        singleLine = true,
        maxLines = maxLine,
        cursorBrush = SolidColor(MaterialTheme.colors.onSurface),
        textStyle = LocalTextStyle.current.copy(
            fontSize = fontSize
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        decorationBox = { innerTextField ->
            Row(Modifier.padding(10.dp,0.dp,0.dp,0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(Modifier.weight(1f)) {
                    if (text.isEmpty())
                        Text(hint,
                            style = LocalTextStyle.current.copy(
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
                            fontSize = fontSize
                        )
                    )
                    innerTextField()
                }
            }
        }
    )
}