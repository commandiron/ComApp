package com.example.chatapp_by_command.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatapp_by_command.presentation.chat.components.chatappbar.IndicatingIconButton

@Composable
fun ProfileAppBar(
    onMorevertLogOutClick: (() -> Unit)? = null
) {

    TopAppBar(
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary
    ) {
        Row(modifier = Modifier
            .fillMaxSize()){

            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column() {
                        Icon(
                            modifier = Modifier
                                .size(30.dp),
                            imageVector = Icons.Default.Chat,
                            contentDescription = "Logo Icon",
                            tint = MaterialTheme.colors.onPrimary
                        )
                        androidx.compose.material3.Text(
                            text = "ComApp",
                            color = MaterialTheme.colors.onPrimary,
                            fontFamily = FontFamily.Cursive,
                            fontSize = 10.sp
                        )
                    }

                }
            }
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
                ProfileAppbarActions(onMorevertLogOutClick = {
                    onMorevertLogOutClick?.invoke()})
            }
        }
    }
}

@Composable
fun ProfileAppbarActions(
    onMorevertLogOutClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier.fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        var showMenu by remember { mutableStateOf(false) }

        IndicatingIconButton(
            onClick = { showMenu = true},
            indication = rememberRipple(bounded = false, radius = 22.dp),
            modifier = Modifier.then(Modifier.padding(end = 10.dp))
        ) {
            Icon(
                imageVector = Icons.Rounded.MoreVert,
                contentDescription = null,
                tint = MaterialTheme.colors.onPrimary
            )
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }) {
                DropdownMenuItem(
                    onClick = {
                    onMorevertLogOutClick?.invoke()}) {
                    Text(
                        text = "Log Out")
                }
            }
        }
    }
}