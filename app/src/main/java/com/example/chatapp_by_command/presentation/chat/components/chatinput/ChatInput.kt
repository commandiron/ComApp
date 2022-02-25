package com.example.chatapp_by_command.presentation.chat.components

import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.chatapp_by_command.presentation.chat.components.chatinput.ChatTextField
import com.google.accompanist.insets.*

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun ChatInput(
    modifier: Modifier = Modifier,
    onMessageChange: (String) -> Unit,
    onFocusEvent: (Boolean) -> Unit) {

    val context = LocalContext.current

    var input by remember { mutableStateOf(TextFieldValue("")) }
    val textEmpty: Boolean by derivedStateOf { input.text.isEmpty() }

    val imePaddingValues = rememberInsetsPaddingValues(insets = LocalWindowInsets.current.ime)

    Row(
        modifier = modifier
            .windowInsetsPadding(WindowInsets(bottom = imePaddingValues.calculateBottomPadding() - 48.dp))
            .padding(horizontal = 8.dp, vertical = 6.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom
    ) {

        ChatTextField(
            modifier = modifier.weight(1f).focusable(true),
            input = input,
            empty = textEmpty,
            onValueChange = {
                input = it
            }, onFocusEvent = {
                onFocusEvent(it)
            }
        )

        Spacer(modifier = Modifier.width(6.dp))

        FloatingActionButton(
            modifier = Modifier.size(48.dp),
            backgroundColor = Color(0xff00897B),
            onClick = {
                if (!textEmpty) {
                    onMessageChange(input.text)
                    input = TextFieldValue("")
                }else{
                    Toast.makeText(context, "Sound Recorder Clicked.\n(Not Available)", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Icon(
                tint = Color.White,
                imageVector = if (textEmpty) Icons.Filled.Mic else Icons.Filled.Send,
                contentDescription = null
            )
        }
    }
}