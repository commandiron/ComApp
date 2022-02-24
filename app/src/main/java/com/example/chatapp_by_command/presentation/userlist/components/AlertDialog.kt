package com.example.chatapp_by_command.presentation.userlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatapp_by_command.presentation.OutlinedTextFieldNameCom

@Composable
fun AlertDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit = {}
) {

    val dialogText = "Add user via email".trimIndent()

    var emailInput by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(emailInput)
                },
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(text = "OK")
            }
        },
        title = {
            Text(text = "Add User", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        },
        text = {
            Column() {
                Text(text = dialogText)
                OutlinedTextFieldNameCom(entry = emailInput, hint = "email", onChange = {emailInput = it})
            }
        }
    )
}