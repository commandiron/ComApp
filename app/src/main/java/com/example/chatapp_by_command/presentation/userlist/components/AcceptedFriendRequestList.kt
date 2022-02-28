package com.example.chatapp_by_command.presentation.userlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MarkEmailUnread
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.chatapp_by_command.domain.model.FriendListUiRow
import com.example.chatapp_by_command.domain.model.enumclasses.MessageStatus
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AcceptedFriendRequestList(
    item: FriendListUiRow,
    onClick:() -> Unit = {}){
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                onClick()
            }
            .padding(2.dp),
        elevation = 5.dp,
    ) {

        Row(modifier = Modifier.height(60.dp), verticalAlignment = Alignment.CenterVertically) {
            Box() {
                Surface(
                    modifier = Modifier.padding(6.dp),
                    shape = CircleShape,
                    color = Color.LightGray
                ) {
                    if (item.userPictureUrl != "") {
                        Image(
                            painter = rememberImagePainter(item.userPictureUrl),
                            contentDescription = null,
                            modifier = Modifier
                                .aspectRatio(1f)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = null,
                            modifier = Modifier
                                .background(Color.Black)
                                .padding(4.dp)
                                .aspectRatio(1f)
                        )
                    }
                }
            }
            Box(modifier = Modifier.padding(4.dp)) {

                val sdf = remember { SimpleDateFormat("hh:mm", Locale.ROOT) }

                if(item.lastMessage.status == MessageStatus.RECEIVED.toString()
                    && item.lastMessage.profileUUID == item.userUUID){

                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                        Column(
                            modifier = Modifier
                                .weight(3f)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center) {
                            Text(text = item.userEmail)
                            Text(text = "Last Message: " + item.lastMessage.message + " " + "(${sdf.format(item.lastMessage.date)})",
                                fontSize = 10.sp,
                                modifier = Modifier.padding(2.dp))


                        }
                        Column(
                            modifier = Modifier.weight(1f)) {
                            Text(text = "New Message",
                                color = Color.Red,
                                fontSize = 10.sp,
                                modifier = Modifier
                                    .padding(2.dp)
                                    .align(Alignment.CenterHorizontally))

                            Icon(
                                imageVector = Icons.Filled.MarkEmailUnread,
                                tint = Color.Red,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(20.dp)
                                    .padding(2.dp)
                                    .align(Alignment.CenterHorizontally))
                        }

                    }
                }else{
                    val dateTimeControl: Long = 0
                    if(!item.lastMessage.date.equals(dateTimeControl)){

                        if(item.lastMessage.profileUUID != item.userUUID){
                            Column {
                                Text(text = item.userEmail)
                                Text(text = "Me: " + item.lastMessage.message + " " + "(${sdf.format(item.lastMessage.date)})",
                                    fontSize = 10.sp,
                                    modifier = Modifier.padding(2.dp))
                            }
                        }else{
                            Column {
                                Text(text = item.userEmail)
                                Text(text = "Last Message: " + item.lastMessage.message + " " + "(${sdf.format(item.lastMessage.date)})",
                                    fontSize = 10.sp,
                                    modifier = Modifier.padding(2.dp))
                            }
                        }

                    }else{
                        Box(modifier = Modifier
                            .fillMaxHeight()) {
                            Text(
                                text = item.userEmail,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(Alignment.Center).padding(2.dp))
                        }
                    }
                }
            }
        }
    }
}