package com.example.chatapp_by_command.domain.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


data class FriendListUiRow(

    val chatRoomUUID: String,

    val userEmail: String = "",

    val userUUID: String = "",

    val registerUUID: String = "",

    val userPictureUrl: String = "",

    val lastMessage: ChatMessage = ChatMessage()



)