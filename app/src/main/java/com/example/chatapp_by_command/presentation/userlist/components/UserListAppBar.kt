package com.example.chatapp_by_command.presentation.userlist

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.PlusOne
import androidx.compose.material.icons.rounded.Summarize
import androidx.compose.material.icons.rounded.Videocam
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatapp_by_command.presentation.chat.components.chatappbar.ChatAppbarActions
import com.example.chatapp_by_command.presentation.chat.components.chatappbar.IndicatingIconButton
import com.example.chatapp_by_command.ui.theme.primaryColor

@Composable
fun UserListAppBar(
    title: String = "Title",
    description: String = "Description",
    onUserAddButtonClick: (() -> Unit)? = null
) {
    val context = LocalContext.current

    TopAppBar(
        elevation = 4.dp,
        backgroundColor = primaryColor,
        contentColor = Color.White
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
                    tint = Color.White,
                    modifier = Modifier
                )
            }
        }
    }
}