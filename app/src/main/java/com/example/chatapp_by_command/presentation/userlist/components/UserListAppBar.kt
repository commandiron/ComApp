package com.example.chatapp_by_command.presentation.userlist

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.chatapp_by_command.presentation.chat.components.chatappbar.IndicatingIconButton

@Composable
fun UserListAppBar(
    title: String = "Title",
    description: String = "Description",
    onUserAddButtonClick: (() -> Unit)? = null
) {
    val context = LocalContext.current

    TopAppBar(
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary
    )
    {
        Box(contentAlignment = Alignment.CenterEnd, modifier = Modifier.fillMaxSize()) {
            IndicatingIconButton(
                onClick = {onUserAddButtonClick?.invoke()},
                indication = rememberRipple(bounded = false, radius = 22.dp),
                modifier = Modifier.then(Modifier.size(44.dp))
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onPrimary,
                    modifier = Modifier
                )
            }
        }
    }
}