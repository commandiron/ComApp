package com.example.chatapp_by_command.presentation.profile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Videocam
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import coil.compose.rememberImagePainter
import com.example.chatapp_by_command.presentation.chat.components.chatappbar.ChatAppbarActions
import com.example.chatapp_by_command.presentation.chat.components.chatappbar.IndicatingIconButton
import com.example.chatapp_by_command.ui.theme.logoColorLight
import com.example.chatapp_by_command.ui.theme.primaryColor

@Composable
fun ProfileAppBar(
    onMorevertLogOutClick: (() -> Unit)? = null
) {

    TopAppBar(
        elevation = 4.dp,
        backgroundColor = primaryColor,
        contentColor = Color.White
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
                            tint = logoColorLight
                        )
                        androidx.compose.material3.Text(
                            text = "ComApp",
                            color = logoColorLight,
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
                tint = Color.White
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